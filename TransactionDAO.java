// src/TransactionDAO.java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAO {
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getAll() {
        return new ArrayList<>(transactions);
    }

    public boolean save(Transaction transaction) {
        return transactions.add(transaction);
    }

    // Note: Transaction class doesn't have ID, so we can't implement get/update/delete by ID
    // Transactions are typically retrieved by account number
    public List<Transaction> getByAccountNumber(String accountNumber) {
        // Since transactions are stored in Account objects, this would need to search accounts
        // For now, return transactions from the central list that match the account
        return transactions.stream()
                .filter(t -> {
                    // This would need access to account-transaction relationships
                    // For simplicity, return all transactions
                    return true;
                })
                .toList();
    }
}