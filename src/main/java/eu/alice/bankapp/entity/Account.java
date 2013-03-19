package eu.alice.bankapp.entity;

public class Account {
    private final String owner;
    private final double balance;

    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

}
