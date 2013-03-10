package bearded.bank.withtry;

import bearded.bank.BankAccessor;
import bearded.bank.BankException;
import bearded.entity.Account;
import bearded.monad.Try;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private Map<String, BankProxy> bankProxies;

    public AccountRepository(Map<String, BankProxy> bankProxies) {
        this.bankProxies = bankProxies;
    }

    public Try<Account> getAccount(String bankName, String accountNumber) {
        BankProxy bankProxy = bankProxies.get(bankName);

        if (bankProxy == null) {
            return Try.Failure(new BankException("unknown bank name" + bankName));
        }

        return bankProxy.getAccountByNumber(accountNumber);
    }

    public static AccountRepository apply(Map<String, BankAccessor> accessors) {
        HashMap<String, BankProxy> bankProxies = new HashMap<>();

        accessors.forEach((bankName, accessor) -> bankProxies.put(bankName, new BankProxy(accessor)));

        return new AccountRepository(bankProxies);
    }

}
