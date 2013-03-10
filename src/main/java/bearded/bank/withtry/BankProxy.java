package bearded.bank.withtry;

import bearded.bank.BankAccessor;
import bearded.bank.BankException;
import bearded.entity.Account;
import bearded.monad.Try;

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
