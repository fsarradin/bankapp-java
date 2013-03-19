package eu.alice.bankapp.bank;

import eu.alice.bankapp.entity.Account;
import eu.alice.bankapp.Try;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;
import java.util.stream.Stream;

import static eu.alice.bankapp.entity.AliceProperties.getAccountNumbersIn;
import static eu.alice.bankapp.entity.AliceProperties.getBankNames;
import static eu.alice.bankapp.Try.Success;

public class BankService {

    private final AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

        Try<Double> total = balancesStream
                .reduce(Success(0.0), this::addBalances);

        if (!total.isSuccess()) {
            return "{ \"error\": \"" + total.getError().getMessage() + "\" }";
        }

        return "{ \"total\": \"" + total.get() + "\" }";

    }


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
