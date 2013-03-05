package bearded.bank.withoption;

import bearded.bank.BankAccessor;
import bearded.entity.Account;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private Map<String, BankProxy> bankProxies;

    public AccountRepository(Map<String, BankProxy> bankProxies) {
        this.bankProxies = bankProxies;
    }

    public Optional<Account> getAccount(String bankName, String accountNumber) {
        Optional<Account> none = Optional.absent();

        return Optional.fromNullable(bankProxies.get(bankName)).transform(proxy ->
                proxy.getAccountByNumber(accountNumber)
        ).or(none);
    }

    public static AccountRepository apply(Map<String, BankAccessor> accessors) {
        HashMap<String, BankProxy> bankProxies = new HashMap<>();

        accessors.forEach((bankName, accessor) -> bankProxies.put(bankName, new BankProxy(accessor)));

        return new AccountRepository(bankProxies);
    }

}
