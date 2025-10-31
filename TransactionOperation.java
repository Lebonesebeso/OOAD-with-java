// src/TransactionOperation.java
public abstract class TransactionOperation {
    protected double amount;

    public TransactionOperation(double amount) {
        this.amount = amount;
    }

    public abstract void execute(Account account);
}