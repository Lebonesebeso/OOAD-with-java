// src/BankingController.java
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankingController {

    // Register Customer
    @FXML private TextField textFieldName, textFieldSurname, textFieldAddress, textFieldEmail, textFieldPhone, textFieldIdNumber;
    @FXML private ComboBox<String> comboBoxDocType;
    @FXML private PasswordField passwordField;
    @FXML private Button registerCustomerButton;

    // Dashboard
    @FXML private ComboBox<String> dashboardAccountTypeCombo, withdrawAccountTypeCombo;
    @FXML private TextField dashboardAccountNumberField, dashboardDepositAmountField;
    @FXML private TextField withdrawAccountNumberField, withdrawAmountField;
    @FXML private Button depositButton, withdrawButton;

    // Open Account
    @FXML private ComboBox<String> openAccountTypeCombo;
    @FXML private TextField openAccountHolderField, openDepositAmountField;
    @FXML private Button createAccountButton;

    // Transactions
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> colAccountNumber, colType;
    @FXML private TableColumn<Transaction, BigDecimal> colAmount;
    @FXML private TableColumn<Transaction, LocalDate> colDate;

    private Customer currentCustomer;
    private final List<Account> accounts = new ArrayList<>();
    private final Branch gaboroneBranch = new Branch("001", "Gaborone Main Branch");
    private String currentUser = "Guest";

    @FXML
    public void initialize() {
        comboBoxDocType.setItems(FXCollections.observableArrayList("Omang", "Passport"));
        var types = FXCollections.observableArrayList("Savings", "Investment", "Cheque");
        dashboardAccountTypeCombo.setItems(types);
        withdrawAccountTypeCombo.setItems(types);
        openAccountTypeCombo.setItems(types);

        colAccountNumber.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getAccountNumber()));
        colAmount.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getAmount()));
        colType.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getType()));
        colDate.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getDate()));

        currentCustomer = new Customer(1, "Kabo", "Sebeso", "Plot 123, Gaborone", "kabo@example.com", "71234567", "123", "Omang", "123456789");
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
        System.out.println("Logged in: " + currentUser);
    }

    @FXML private void handleRegisterCustomer() {
        AlertUtil.info("Customer registered!");
    }

    @FXML private void handleDeposit() {
        String accNum = dashboardAccountNumberField.getText();
        String amtStr = dashboardDepositAmountField.getText();
        if (accNum.isEmpty() || amtStr.isEmpty()) {
            AlertUtil.error("Fill all fields.");
            return;
        }
        try {
            Account acc = findAccount(accNum);
            if (acc != null) {
                acc.deposit(Double.parseDouble(amtStr));
                updateTransactions();
                AlertUtil.info("Deposited " + amtStr + " BWP!");
            }
        } catch (NumberFormatException e) {
            AlertUtil.error("Invalid amount.");
        }
    }

    @FXML private void handleWithdraw() {
        String accNum = withdrawAccountNumberField.getText();
        String amtStr = withdrawAmountField.getText();
        if (accNum.isEmpty() || amtStr.isEmpty()) {
            AlertUtil.error("Fill all fields.");
            return;
        }
        try {
            Account acc = findAccount(accNum);
            if (acc != null) {
                acc.withdraw(Double.parseDouble(amtStr));
                updateTransactions();
                AlertUtil.info("Withdrawn " + amtStr + " BWP!");
            }
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML private void handleCreateAccount() {
        String type = openAccountTypeCombo.getValue();
        String amtStr = openDepositAmountField.getText();
        if (type == null || amtStr.isEmpty()) {
            AlertUtil.error("Fill all fields.");
            return;
        }
        try {
            double amount = Double.parseDouble(amtStr);
            Account acc = createAccount(type, amount);
            if (acc != null) {
                accounts.add(acc);
                currentCustomer.addAccount(acc);
                updateTransactions();
                AlertUtil.info("Created " + type + " account!");
            }
        } catch (NumberFormatException e) {
            AlertUtil.error("Invalid amount.");
        }
    }

    private Account createAccount(String type, double amount) {
        if (amount < 50.0) {
            AlertUtil.error("Minimum 50 BWP");
            return null;
        }
        String num = type.substring(0, 1) + "A" + (accounts.size() + 1);
        return switch (type) {
            case "Savings" -> new SavingsAccount(num, amount, currentCustomer, gaboroneBranch);
            case "Investment" -> new InvestmentAccount(num, amount, currentCustomer, gaboroneBranch);
            case "Cheque" -> new ChequeAccount(num, amount, currentCustomer, gaboroneBranch, "BAC", "Gaborone");
            default -> null;
        };
    }

    private Account findAccount(String num) {
        return accounts.stream().filter(a -> a.getAccountNumber().equals(num)).findFirst().orElse(null);
    }

    private void updateTransactions() {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        accounts.forEach(a -> list.addAll(a.getTransactions()));
        transactionTable.setItems(list);
    }
}