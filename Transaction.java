// src/Transaction.java
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private BigDecimal amount;
    private LocalDate date;
    private String type;

    public Transaction(BigDecimal amount, LocalDate date, String type) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.date = date;
        this.type = type;
    }

    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}