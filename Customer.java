// src/Customer.java
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String surname;
    private String address;
    private String email;
    private String phone;
    private String password;
    private String idType;
    private String idNumber;
    private List<Account> accounts = new ArrayList<>();

    public Customer(int id, String firstName, String surname, String address, String email, String phone, String password, String idType, String idNumber) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password reset done!");
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getIdType() { return idType; }
    public String getIdNumber() { return idNumber; }
    public List<Account> getAccounts() { return accounts; }

    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}