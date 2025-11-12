// src/Deposit.java
import java.time.LocalDate;

public class Deposit extends TransactionOperation {
    public Deposit(double amount) {
        super(amount);
    }

    @Override
    public void execute(Account account) {
        if (amount < 50.0) {
            System.out.println("Error: Minimum deposit is 50 BWP.");
            return;
        }
        account.getBalance().add(java.math.BigDecimal.valueOf(amount));
        account.getTransactions().add(new Transaction(java.math.BigDecimal.valueOf(amount), LocalDate.now(), "Deposit"));
        System.out.println("Notification: Deposited " + amount + " BWP.");
    }
}