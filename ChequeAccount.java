// src/ChequeAccount.java
import java.time.LocalDate;

public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double initialDeposit, Customer customer, Branch branch, String employerName, String employerAddress) {
        super(accountNumber, initialDeposit, customer, branch);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        TransactionOperation withdrawal = new Withdrawal(amount);
        withdrawal.execute(this);
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}