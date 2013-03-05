package bearded.bank.base;

import bearded.bank.BankAccessor;
import bearded.entity.Account;

public class BankProxy {

    private BankAccessor bankAccessor;

    public BankProxy(BankAccessor bankAccessor) {
        this.bankAccessor = bankAccessor;
    }

    public Account getAccountByNumber(String accountNumber) {
        return bankAccessor.getAccountByNumber(accountNumber);
    }

}
