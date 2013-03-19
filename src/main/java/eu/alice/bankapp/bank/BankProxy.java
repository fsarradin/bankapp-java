package eu.alice.bankapp.bank;

import eu.alice.bankapp.entity.Account;
import eu.alice.bankapp.monad.Try;

public class BankProxy {

    private BankAccessor bankAccessor;

    public BankProxy(BankAccessor bankAccessor) {
        this.bankAccessor = bankAccessor;
    }

    public Try<Account> getAccountByNumber(String accountNumber) {
        Account account = bankAccessor.getAccountByNumber(accountNumber);

        if (account == null) {
            return Try.Failure(new BankException("unknown account number " + accountNumber));
        }

        return Try.Success(account);
    }

}
