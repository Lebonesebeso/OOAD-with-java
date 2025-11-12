// src/IntegrationTest.java
import java.math.BigDecimal;

public class IntegrationTest {
    private DatabaseManager db;
    private BankingService bankingService;
    
    public IntegrationTest() {
        this.db = DatabaseManager.getInstance();
        this.bankingService = new BankingService();
    }
    
    public void runAllTests() {
        System.out.println("=== BAC BANKING SYSTEM INTEGRATION TESTS ===\n");
        
        testCustomerRegistration();
        testUserLogin();
        testAccountCreation();
        testDepositTransaction();
        testWithdrawalTransaction();
        testFundTransfer();
        testInterestCalculation();
        
        System.out.println("\n=== INTEGRATION TEST SUMMARY ===");
    }
    
    private void testCustomerRegistration() {
        System.out.println("TEST 1: CUSTOMER REGISTRATION");
        System.out.println("----------------------------------------");
        
        try {
            // Create new customer
            Customer newCustomer = new Customer(
                0, "Test", "User", "123 Test St", 
                "test@email.com", "71112233", "test123", 
                "Omang", "555555555"
            );
            
            // Save through service layer
            boolean registrationSuccess = bankingService.registerCustomer(newCustomer);
            
            // Verify through DAO
            Customer savedCustomer = bankingService.getCustomerByEmail("test@email.com").orElse(null);
            
            if (registrationSuccess && savedCustomer != null && savedCustomer.getId() > 0) {
                System.out.println("✓ PASS: Customer registered successfully");
                System.out.println("  Customer ID: " + savedCustomer.getId());
                System.out.println("  Email: " + savedCustomer.getEmail());
            } else {
                System.out.println("✗ FAIL: Customer registration failed");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Customer registration error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testUserLogin() {
        System.out.println("TEST 2: USER LOGIN AUTHENTICATION");
        System.out.println("----------------------------------------");
        
        try {
            // Test valid login
            boolean validLogin = bankingService.authenticateCustomer("test@email.com", "test123");
            
            // Test invalid login
            boolean invalidLogin = bankingService.authenticateCustomer("test@email.com", "wrongpass");
            
            if (validLogin && !invalidLogin) {
                System.out.println("✓ PASS: Login authentication works correctly");
                System.out.println("  Valid credentials: " + validLogin);
                System.out.println("  Invalid credentials: " + invalidLogin);
            } else {
                System.out.println("✗ FAIL: Login authentication failed");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Login test error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testAccountCreation() {
        System.out.println("TEST 3: ACCOUNT CREATION");
        System.out.println("----------------------------------------");
        
        try {
            Customer customer = bankingService.getCustomerByEmail("test@email.com").orElse(null);
            Branch branch = bankingService.getBranch("GAB01").orElse(null);
            
            if (customer != null && branch != null) {
                // Create different account types
                SavingsAccount savings = new SavingsAccount("SAV1001", 1000.0, customer, branch);
                CurrentAccount current = new CurrentAccount("CUR1001", 500.0, customer, branch);
                
                boolean savingsCreated = bankingService.createAccount(savings);
                boolean currentCreated = bankingService.createAccount(current);
                
                // Verify accounts were created
                List<Account> customerAccounts = bankingService.getCustomerAccounts(customer.getId());
                
                if (savingsCreated && currentCreated && customerAccounts.size() >= 2) {
                    System.out.println("✓ PASS: Multiple accounts created successfully");
                    System.out.println("  Accounts created: " + customerAccounts.size());
                    for (Account acc : customerAccounts) {
                        System.out.println("    - " + acc.getAccountNumber() + " (" + acc.getAccountType() + ")");
                    }
                } else {
                    System.out.println("✗ FAIL: Account creation failed");
                }
            } else {
                System.out.println("✗ FAIL: Could not find customer or branch for account creation");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Account creation error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testDepositTransaction() {
        System.out.println("TEST 4: DEPOSIT TRANSACTION");
        System.out.println("----------------------------------------");
        
        try {
            String accountNumber = "CUR1001";
            double depositAmount = 250.0;
            
            // Get initial balance
            BigDecimal initialBalance = bankingService.getAccountBalance(accountNumber);
            
            // Perform deposit
            boolean depositSuccess = bankingService.deposit(accountNumber, depositAmount);
            
            // Get updated balance
            BigDecimal updatedBalance = bankingService.getAccountBalance(accountNumber);
            BigDecimal expectedBalance = initialBalance.add(BigDecimal.valueOf(depositAmount));
            
            // Get transaction history
            List<Transaction> transactions = bankingService.getAccountTransactions(accountNumber);
            
            if (depositSuccess && updatedBalance.equals(expectedBalance)) {
                System.out.println("✓ PASS: Deposit transaction completed successfully");
                System.out.println("  Initial balance: " + initialBalance + " BWP");
                System.out.println("  Deposit amount: " + depositAmount + " BWP");
                System.out.println("  Final balance: " + updatedBalance + " BWP");
                System.out.println("  Transactions recorded: " + transactions.size());
            } else {
                System.out.println("✗ FAIL: Deposit transaction failed");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Deposit test error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testWithdrawalTransaction() {
        System.out.println("TEST 5: WITHDRAWAL TRANSACTION");
        System.out.println("----------------------------------------");
        
        try {
            String accountNumber = "CUR1001";
            double withdrawalAmount = 100.0;
            
            // Get initial balance
            BigDecimal initialBalance = bankingService.getAccountBalance(accountNumber);
            
            // Perform withdrawal
            boolean withdrawalSuccess = bankingService.withdraw(accountNumber, withdrawalAmount);
            
            // Get updated balance
            BigDecimal updatedBalance = bankingService.getAccountBalance(accountNumber);
            BigDecimal expectedBalance = initialBalance.subtract(BigDecimal.valueOf(withdrawalAmount));
            
            if (withdrawalSuccess && updatedBalance.equals(expectedBalance)) {
                System.out.println("✓ PASS: Withdrawal transaction completed successfully");
                System.out.println("  Initial balance: " + initialBalance + " BWP");
                System.out.println("  Withdrawal amount: " + withdrawalAmount + " BWP");
                System.out.println("  Final balance: " + updatedBalance + " BWP");
            } else {
                System.out.println("✗ FAIL: Withdrawal transaction failed");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Withdrawal test error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testFundTransfer() {
        System.out.println("TEST 6: FUND TRANSFER BETWEEN ACCOUNTS");
        System.out.println("----------------------------------------");
        
        try {
            String fromAccount = "CUR1001";
            String toAccount = "SAV1001";
            double transferAmount = 150.0;
            
            // Get initial balances
            BigDecimal fromInitialBalance = bankingService.getAccountBalance(fromAccount);
            BigDecimal toInitialBalance = bankingService.getAccountBalance(toAccount);
            
            // Perform transfer
            boolean transferSuccess = bankingService.transfer(fromAccount, toAccount, transferAmount);
            
            // Get updated balances
            BigDecimal fromFinalBalance = bankingService.getAccountBalance(fromAccount);
            BigDecimal toFinalBalance = bankingService.getAccountBalance(toAccount);
            
            BigDecimal fromExpected = fromInitialBalance.subtract(BigDecimal.valueOf(transferAmount));
            BigDecimal toExpected = toInitialBalance.add(BigDecimal.valueOf(transferAmount));
            
            if (transferSuccess && fromFinalBalance.equals(fromExpected) && toFinalBalance.equals(toExpected)) {
                System.out.println("✓ PASS: Fund transfer completed successfully");
                System.out.println("  From account: " + fromAccount);
                System.out.println("  To account: " + toAccount);
                System.out.println("  Transfer amount: " + transferAmount + " BWP");
                System.out.println("  From balance: " + fromInitialBalance + " → " + fromFinalBalance + " BWP");
                System.out.println("  To balance: " + toInitialBalance + " → " + toFinalBalance + " BWP");
            } else {
                System.out.println("✗ FAIL: Fund transfer failed");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Transfer test error - " + e.getMessage());
        }
        System.out.println();
    }
    
    private void testInterestCalculation() {
        System.out.println("TEST 7: INTEREST CALCULATION");
        System.out.println("----------------------------------------");
        
        try {
            String accountNumber = "SAV1001";
            
            // Get account and initial balance
            Account account = bankingService.getAccount(accountNumber).orElse(null);
            BigDecimal initialBalance = bankingService.getAccountBalance(accountNumber);
            
            if (account instanceof InterestBearing) {
                InterestBearing interestAccount = (InterestBearing) account;
                
                // Apply interest
                interestAccount.addInterest();
                
                // Get updated balance
                BigDecimal updatedBalance = bankingService.getAccountBalance(accountNumber);
                
                if (updatedBalance.compareTo(initialBalance) > 0) {
                    System.out.println("✓ PASS: Interest calculation completed successfully");
                    System.out.println("  Account: " + accountNumber);
                    System.out.println("  Initial balance: " + initialBalance + " BWP");
                    System.out.println("  Balance after interest: " + updatedBalance + " BWP");
                    System.out.println("  Interest earned: " + updatedBalance.subtract(initialBalance) + " BWP");
                } else {
                    System.out.println("✗ FAIL: Interest was not applied correctly");
                }
            } else {
                System.out.println("✗ FAIL: Account does not support interest");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Interest calculation error - " + e.getMessage());
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        IntegrationTest test = new IntegrationTest();
        test.runAllTests();
    }
}