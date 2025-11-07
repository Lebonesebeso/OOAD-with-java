// src/AccountDAO.java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAO {
    private List<Account> accounts = new ArrayList<>();

    public Optional<Account> get(String accountNumber) {
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts);
    }

    public boolean save(Account account) {
        if (get(account.getAccountNumber()).isPresent()) {
            return false;
        }
        return accounts.add(account);
    }

    public boolean update(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(account.getAccountNumber())) {
                accounts.set(i, account);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String accountNumber) {
        return accounts.removeIf(a -> a.getAccountNumber().equals(accountNumber));
    }

    public List<Account> getByCustomerId(int customerId) {
        return accounts.stream()
                .filter(a -> a.getCustomer().getId() == customerId)
                .toList();
    }

    public boolean accountExists(String accountNumber) {
        return get(accountNumber).isPresent();
    }
}