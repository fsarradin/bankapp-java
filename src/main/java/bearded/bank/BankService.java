package bearded.bank;

import bearded.bank.withoption.AccountRepository;
import bearded.entity.Account;
import bearded.entity.AliceProperties;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

import static bearded.entity.AliceProperties.PRINCIPAL_ACCOUNT;
import static bearded.entity.AliceProperties.PRINCIPAL_BANK;
import static com.google.common.collect.FluentIterable.from;

public class BankService {

    private final AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getPrincipalBalance(HttpExchange httpExchange) throws IOException {
        Optional<Account> account = accountRepository.getAccount(PRINCIPAL_BANK, PRINCIPAL_ACCOUNT);

        if (!account.isPresent()) {
            return "{ \"error\": \"bad value\" }";
        }
        return "{ \"balance\": \"" + account.get().getBalance() + "\" }";
    }

    public String getTotalBalance(HttpExchange httpExchange) throws IOException {
        FluentIterable<Optional<Double>> balances =
                from(AliceProperties.getBankNames()).transformAndConcat(bankName ->
                        from(AliceProperties.getAccountNumbersIn(bankName)).transform(accountNumber ->
                                accountRepository.getAccount(bankName, accountNumber).transform(Account::getBalance)
                        )
                );

        if (balances.filter(o -> !o.isPresent()).size() > 0) {
            return "{ \"error\": \"bad value\" }";
        }

        Double sum = balances
                .transformAndConcat(o -> o.asSet())
                .toImmutableList().stream()
                .reduce(0.0, (s, b) -> s + b);
        return "{ \"total\": \"" + sum + "\" }";

    }

    public String getBalanceByBank(HttpExchange httpExchange) throws IOException {
        FluentIterable<String> bankInfos =
                from(AliceProperties.getBankNames()).transform(bankName -> {
                    FluentIterable<Optional<Double>> balances =
                            from(AliceProperties.getAccountNumbersIn(bankName)).transform(accountNumber ->
                                    accountRepository.getAccount(bankName, accountNumber).transform(Account::getBalance)
                            );

                    if (balances.filter(o -> !o.isPresent()).size() > 0) {
                        return "{\"name\": \"" + bankName + "\", \"error\": \"bad value\" }";
                    }

                    Double sum = balances
                            .transformAndConcat(o -> o.asSet())
                            .toImmutableList().stream()
                            .reduce(0.0, (s, b) -> s + b);
                    return "{\"name\": \"" + bankName + "\", \"balance\": \"" + sum + "\" }";
                });

        return "[" + String.join(", ", bankInfos) + "]";
    }

    public static BankService createBankService(Map<String, BankAccessor> bankAccessors) {
        return new BankService(AccountRepository.apply(bankAccessors));
    }

}
