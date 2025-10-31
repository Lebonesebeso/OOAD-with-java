// src/Withdrawal.java
import java.time.LocalDate;

public class Withdrawal extends TransactionOperation {
    public Withdrawal(double amount) {
        super(amount);
    }

    @Override
    public void execute(Account account) {
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive.");
            return;
        }
        if (account.getBalance().compareTo(java.math.BigDecimal.valueOf(amount)) < 0) {
            System.out.println("Error: Insufficient funds.");
            return;
        }
        account.getBalance().subtract(java.math.BigDecimal.valueOf(amount));
        account.getTransactions().add(new Transaction(java.math.BigDecimal.valueOf(amount).negate(), LocalDate.now(), "Withdrawal"));
        System.out.println("Notification: Withdrawn " + amount + " BWP.");
    }
}