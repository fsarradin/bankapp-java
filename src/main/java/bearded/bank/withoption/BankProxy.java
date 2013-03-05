package bearded.bank.withoption;

import bearded.bank.BankAccessor;
import bearded.entity.Account;
import com.google.common.base.Optional;

public class BankProxy {

    private BankAccessor bankAccessor;

    public BankProxy(BankAccessor bankAccessor) {
        this.bankAccessor = bankAccessor;
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return Optional.fromNullable(bankAccessor.getAccountByNumber(accountNumber));
    }

}
