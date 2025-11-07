// src/CustomerDAO.java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {
    private List<Customer> customers = new ArrayList<>();
    private int nextId = 1;

    public Optional<Customer> get(String id) {
        try {
            int customerId = Integer.parseInt(id);
            return customers.stream()
                    .filter(c -> c.getId() == customerId)
                    .findFirst();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public List<Customer> getAll() {
        return new ArrayList<>(customers);
    }

    public boolean save(Customer customer) {
        if (customer.getId() == 0) {
            Customer newCustomer = new Customer(
                nextId++,
                customer.getFirstName(),
                customer.getSurname(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPassword(),
                customer.getIdType(),
                customer.getIdNumber()
            );
            return customers.add(newCustomer);
        }
        return customers.add(customer);
    }

    public boolean update(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == customer.getId()) {
                customers.set(i, customer);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String id) {
        try {
            int customerId = Integer.parseInt(id);
            return customers.removeIf(c -> c.getId() == customerId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Optional<Customer> getByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean validateLogin(String email, String password) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email) && 
                              c.getPassword().equals(password));
    }
}