package bearded.bank;

import bearded.bank.base.AccountRepository;
import bearded.entity.Account;
import bearded.entity.AliceProperties;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static bearded.entity.AliceProperties.PRINCIPAL_ACCOUNT;
import static bearded.entity.AliceProperties.PRINCIPAL_BANK;
import static bearded.http.MyHttpServer.closeWith;

public class BankService {

    private final AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getPrincipalBalance(HttpExchange httpExchange) throws IOException {
        Account account = accountRepository.getAccount(PRINCIPAL_BANK, PRINCIPAL_ACCOUNT);

        return "{ \"balance\": \"" + account.getBalance() + "\" }";
    }

    public String getTotalBalance(HttpExchange httpExchange) throws IOException {
        double totalBalance = 0.0;

        for (String bankName : AliceProperties.getBankNames()) {
            for (String accountNumber : AliceProperties.getAccountNumbersIn(bankName)) {
                Account account = accountRepository.getAccount(bankName, accountNumber);
                if (account != null) {
                    totalBalance += account.getBalance();
                }
            }
        }

        return "{ \"total\": \"" + totalBalance + "\" }";

    }

    public String getBalanceByBank(HttpExchange httpExchange) throws IOException {
        Set<String> bankInfo = new HashSet<>();

        for (String bankName : AliceProperties.getBankNames()) {
            double totalBalance = 0.0;

            for (String accountNumber : AliceProperties.getAccountNumbersIn(bankName)) {
                Account account = accountRepository.getAccount(bankName, accountNumber);
                if (account != null) {
                    totalBalance += account.getBalance();
                }
            }

            bankInfo.add("{\"name\":\"" + bankName + "\", \"balance\":\"" + totalBalance + "\"}");
        }

        return "[" + String.join(", ", bankInfo) + "]";
    }

}
