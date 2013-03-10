package bearded.bank;

import bearded.bank.withtry.AccountRepository;
import bearded.entity.Account;
import bearded.monad.Try;
import bearded.monad.Validation;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bearded.entity.AliceProperties.*;

public class BankService {

    private final AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /*
     * PRINCIPAL BALANCE
     *
     */

    public String getPrincipalBalance(HttpExchange httpExchange) {
        Try<Account> account = accountRepository.getAccount(PRINCIPAL_BANK, PRINCIPAL_ACCOUNT);

        if (!account.isSuccess()) {
            return "{ \"error\": \"" + account.getError().getMessage() + "\" }";
        }
        return "{ \"balance\": \"" + account.map(Account::getBalance) + "\" }";
    }


    /*
     * TOTAL BALANCE
     *
     */

    public String getTotalBalance(HttpExchange httpExchange) {
        Stream<Try<Double>> balancesStream =
                getBankNames().stream().flatMap(bankName ->
                        getAccountNumbersIn(bankName).stream().map(accountNumber ->
                                accountRepository
                                        .getAccount(bankName, accountNumber)
                                        .map(Account::getBalance)
                        )
                );

        Try<Double> sum = balancesStream
                .reduce(Try.Success(0.0), this::addBalances);

        if (!sum.isSuccess()) {
            return "{ \"error\": \"" + sum.getError().getMessage() + "\" }";
        }

        return "{ \"total\": \"" + sum.get() + "\" }";

    }


    /*
     * BALANCE BY BANK
     *
     */

    public String getBalanceByBank(HttpExchange httpExchange) {
        List<String> bankInfos =
                getBankNames().stream().map(bankName -> {
                    Try<Double> sum = getBalancesFor(bankName)
                            .reduce(Try.Success(0.0), this::addBalances);

                    if (!sum.isSuccess()) {
                        return "{\"name\": \"" + bankName + "\", \"error\": \"" + sum.getError().getMessage() + "\" }";
                    }

                    return "{\"name\": \"" + bankName + "\", \"balance\": \"" + sum.get() + "\" }";
                }
                ).collect(Collectors.toList());

        return "[" + String.join(", ", bankInfos) + "]";
    }

    private Stream<Try<Double>> getBalancesFor(String bankName) {
        return getAccountNumbersIn(bankName).stream().map(accountNumber ->
                accountRepository.getAccount(bankName, accountNumber)
                        .map(Account::getBalance)
        );
    }


    /*
     * OTHERS...
     *
     */

    private Try<Double> addBalances(Try<Double> m1,
                                    Try<Double> m2) {
        if (m1.isSuccess() || m2.isSuccess()) {
            return m1.flatMap(x -> m2.map(y -> x + y));
        }

        return Try.Failure(
                new BankException(
                        m1.getError().getMessage() + "," + m2.getError().getMessage()
                ));
    }


    public static BankService createBankService(Map<String, BankAccessor> bankAccessors) {
        return new BankService(AccountRepository.apply(bankAccessors));
    }

}
