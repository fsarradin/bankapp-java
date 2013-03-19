package eu.alice.bankapp.bank;

import eu.alice.bankapp.entity.Account;

import java.util.Map;

public class BankAccessor {

    private final Map<String, Account> bankAccounts;

    public BankAccessor(Map<String, Account> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        return bankAccounts.get(accountNumber);
    }

}
