// src/Account.java
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private BigDecimal balance;
    private LocalDate openDate;
    private Customer customer;
    private Branch branch;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, double initialDeposit, Customer customer, Branch branch) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.valueOf(initialDeposit).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.openDate = LocalDate.now();
        this.customer = customer;
        this.branch = branch;
        transactions.add(new Transaction(BigDecimal.valueOf(initialDeposit).setScale(2, BigDecimal.ROUND_HALF_EVEN), openDate, "Opening Deposit"));
    }

    public void deposit(double amount) {
        TransactionOperation deposit = new Deposit(amount);
        deposit.execute(this);
    }

    public abstract void withdraw(double amount);

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public LocalDate getOpenDate() { return openDate; }
    public Customer getCustomer() { return customer; }
    public Branch getBranch() { return branch; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setBalance(double balance) { this.balance = BigDecimal.valueOf(balance).setScale(2, BigDecimal.ROUND_HALF_EVEN); }
}