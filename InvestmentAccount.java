// src/InvestmentAccount.java
import java.time.LocalDate;
import java.math.BigDecimal;

public class InvestmentAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.05;
    private static final double MIN_INITIAL = 500.0;

    public InvestmentAccount(String accountNumber, double initialDeposit, Customer customer, Branch branch) {
        super(accountNumber, initialDeposit, customer, branch);
        if (getBalance().compareTo(BigDecimal.valueOf(MIN_INITIAL)) < 0) {
            throw new IllegalArgumentException("Oops! Need at least 500 BWP to start an Investment Account.");
        }
    }

    @Override
    public void withdraw(double amount) {
        TransactionOperation withdrawal = new Withdrawal(amount);
        withdrawal.execute(this);
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