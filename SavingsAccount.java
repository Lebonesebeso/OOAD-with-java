// src/SavingsAccount.java
import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005;
    private static final double MIN_INITIAL = 500.0;

    public SavingsAccount(String accountNumber, double initialDeposit, Customer customer, Branch branch) {
        super(accountNumber, initialDeposit, customer, branch);
        if (getBalance().compareTo(BigDecimal.valueOf(MIN_INITIAL)) < 0) {
            throw new IllegalArgumentException("Oops! Need at least 500 BWP to start a Savings Account.");
        }
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Sorry! You can't take money out of a Savings Account.");
    }

    @Override
    public void addInterest() {
        BigDecimal interest = getBalance().multiply(BigDecimal.valueOf(INTEREST_RATE)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        deposit(interest.doubleValue());
        Transaction last = getTransactions().get(getTransactions().size() - 1);
        last.setType("Interest");
        System.out.println("Notification: Interest added: " + interest + " BWP.");
    }
}