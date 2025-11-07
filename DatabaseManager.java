// src/DatabaseManager.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    
    // Database configuration
    private static final String URL = "jdbc:sqlite:banking_system.db";
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create connection
            connection = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLite database successfully!");
            
            // Create tables
            createTables();
            
            // Insert sample data
            insertSampleData();
            
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
    
    private void createTables() {
        String[] createTableSQL = {
            // Customers table
            """
            CREATE TABLE IF NOT EXISTS customers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                surname TEXT NOT NULL,
                address TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                phone TEXT NOT NULL,
                password TEXT NOT NULL,
                id_type TEXT NOT NULL,
                id_number TEXT UNIQUE NOT NULL
            )
            """,
            
            // Branches table
            """
            CREATE TABLE IF NOT EXISTS branches (
                code TEXT PRIMARY KEY,
                address TEXT NOT NULL
            )
            """,
            
            // Accounts table
            """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number TEXT PRIMARY KEY,
                account_type TEXT NOT NULL,
                balance REAL NOT NULL,
                open_date TEXT NOT NULL,
                customer_id INTEGER NOT NULL,
                branch_code TEXT NOT NULL,
                employer_name TEXT,
                employer_address TEXT,
                FOREIGN KEY (customer_id) REFERENCES customers (id),
                FOREIGN KEY (branch_code) REFERENCES branches (code)
            )
            """,
            
            // Transactions table
            """
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                account_number TEXT NOT NULL,
                amount REAL NOT NULL,
                transaction_date TEXT NOT NULL,
                type TEXT NOT NULL,
                FOREIGN KEY (account_number) REFERENCES accounts (account_number)
            )
            """
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String sql : createTableSQL) {
                stmt.execute(sql);
            }
            System.out.println("Database tables created successfully!");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    private void insertSampleData() {
        try {
            // Check if sample data already exists
            if (!hasData("customers")) {
                insertSampleCustomers();
                insertSampleBranches();
                insertSampleAccounts();
                insertSampleTransactions();
                System.out.println("Sample data inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
        }
    }
    
    private boolean hasData(String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt(1) > 0;
        }
    }
    
    private void insertSampleCustomers() throws SQLException {
        String sql = """
            INSERT INTO customers (first_name, surname, address, email, phone, password, id_type, id_number)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        String[][] customers = {
            {"Kabo", "Mogomotsi", "Gaborone", "kabo@email.com", "71112233", "123", "Omang", "123456789"},
            {"John", "Doe", "Francistown", "john@email.com", "72223344", "password", "Omang", "987654321"},
            {"Mary", "Smith", "Maun", "mary@email.com", "73334455", "pass123", "Passport", "555555555"}
        };
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String[] customer : customers) {
                pstmt.setString(1, customer[0]);
                pstmt.setString(2, customer[1]);
                pstmt.setString(3, customer[2]);
                pstmt.setString(4, customer[3]);
                pstmt.setString(5, customer[4]);
                pstmt.setString(6, customer[5]);
                pstmt.setString(7, customer[6]);
                pstmt.setString(8, customer[7]);
                pstmt.executeUpdate();
            }
        }
    }
    
    private void insertSampleBranches() throws SQLException {
        String sql = "INSERT INTO branches (code, address) VALUES (?, ?)";
        
        String[][] branches = {
            {"GAB01", "Gaborone Main Branch, CBD"},
            {"FRN01", "Francistown Central, Plot 123"},
            {"MAU01", "Maun Branch, Mall Complex"}
        };
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String[] branch : branches) {
                pstmt.setString(1, branch[0]);
                pstmt.setString(2, branch[1]);
                pstmt.executeUpdate();
            }
        }
    }
    
    private void insertSampleAccounts() throws SQLException {
        String sql = """
            INSERT INTO accounts (account_number, account_type, balance, open_date, customer_id, branch_code, employer_name, employer_address)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        String[][] accounts = {
            {"SAV001", "SAVINGS", "1000.0", "2024-01-15", "1", "GAB01", null, null},
            {"CUR001", "CURRENT", "500.0", "2024-01-16", "1", "GAB01", null, null},
            {"INV001", "INVESTMENT", "2000.0", "2024-01-17", "2", "FRN01", null, null},
            {"CHQ001", "CHEQUE", "750.0", "2024-01-18", "1", "GAB01", "BAC Corporation", "Gaborone CBD"}
        };
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String[] account : accounts) {
                pstmt.setString(1, account[0]);
                pstmt.setString(2, account[1]);
                pstmt.setDouble(3, Double.parseDouble(account[2]));
                pstmt.setString(4, account[3]);
                pstmt.setInt(5, Integer.parseInt(account[4]));
                pstmt.setString(6, account[5]);
                pstmt.setString(7, account[6]);
                pstmt.setString(8, account[7]);
                pstmt.executeUpdate();
            }
        }
    }
    
    private void insertSampleTransactions() throws SQLException {
        String sql = """
            INSERT INTO transactions (account_number, amount, transaction_date, type)
            VALUES (?, ?, ?, ?)
            """;
        
        String[][] transactions = {
            {"SAV001", "1000.0", "2024-01-15", "OPENING_DEPOSIT"},
            {"CUR001", "500.0", "2024-01-16", "OPENING_DEPOSIT"},
            {"INV001", "2000.0", "2024-01-17", "OPENING_DEPOSIT"},
            {"CHQ001", "750.0", "2024-01-18", "OPENING_DEPOSIT"},
            {"CUR001", "250.0", "2024-01-19", "DEPOSIT"},
            {"CUR001", "100.0", "2024-01-20", "WITHDRAWAL"}
        };
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String[] transaction : transactions) {
                pstmt.setString(1, transaction[0]);
                pstmt.setDouble(2, Double.parseDouble(transaction[1]));
                pstmt.setString(3, transaction[2]);
                pstmt.setString(4, transaction[3]);
                pstmt.executeUpdate();
            }
        }
    }
    
    // Database operations
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    // Utility methods
    public void resetDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM transactions");
            stmt.execute("DELETE FROM accounts");
            stmt.execute("DELETE FROM customers");
            stmt.execute("DELETE FROM branches");
            insertSampleData();
            System.out.println("Database reset successfully!");
        } catch (SQLException e) {
            System.err.println("Error resetting database: " + e.getMessage());
        }
    }
    
    public void backupDatabase(String backupPath) {
        try {
            String backupSQL = "BACKUP TO " + backupPath;
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(backupSQL);
                System.out.println("Database backed up to: " + backupPath);
            }
        } catch (SQLException e) {
            System.err.println("Error backing up database: " + e.getMessage());
        }
    }
}