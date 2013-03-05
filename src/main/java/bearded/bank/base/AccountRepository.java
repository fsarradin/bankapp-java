package bearded.bank.base;

import bearded.bank.BankAccessor;
import bearded.entity.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private Map<String, BankProxy> bankProxies;

    public AccountRepository(Map<String, BankProxy> bankProxies) {
        this.bankProxies = bankProxies;
    }

    public Account getAccount(String bankName, String accountNumber) {
        if (!bankProxies.containsKey(bankName)) {
            return null;
        }

        BankProxy bankProxy = bankProxies.get(bankName);
        Account account = bankProxy.getAccountByNumber(accountNumber);

        return account;
    }

    public static AccountRepository apply(Map<String, BankAccessor> accessors) {
        HashMap<String, BankProxy> bankProxies = new HashMap<>();

        accessors.forEach((bankName, accessor) -> bankProxies.put(bankName, new BankProxy(accessor)));

        return new AccountRepository(bankProxies);
    }

}
