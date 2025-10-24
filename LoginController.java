import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel; // Add this if you have it in FXML

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText();

        System.out.println("Login attempted: " + user + " / " + pass); // Debug line

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Please enter username and password.");
            return;
        }

        if (user.equals("kabo") && pass.equals("123")) {
            System.out.println("Login successful!"); // Debug line
            loadDashboard(user);
        } else {
            showError("Invalid login. Try: kabo / 123");
        }
    }

    @FXML
    private void handleRegister() {
        showInfo("Register new customers in Dashboard after login.");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDashboard(String username) {
        try {
            // Try different possible paths for the dashboard
            Parent root = null;
            
            // Option 1: Look in fxml folder
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
                System.out.println("Loaded /fxml/dashboard.fxml");
            } catch (Exception e) {
                System.out.println("Could not load /fxml/dashboard.fxml: " + e.getMessage());
            }
            
            // Option 2: Look in current directory
            if (root == null) {
                try {
                    root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    System.out.println("Loaded dashboard.fxml");
                } catch (Exception e) {
                    System.out.println("Could not load dashboard.fxml: " + e.getMessage());
                }
            }
            
            // Option 3: Look for Banking.fxml instead
            if (root == null) {
                try {
                    root = FXMLLoader.load(getClass().getResource("Banking.fxml"));
                    System.out.println("Loaded Banking.fxml");
                } catch (Exception e) {
                    System.out.println("Could not load Banking.fxml: " + e.getMessage());
                }
            }
            
            // Option 4: Look for banking.fxml (lowercase)
            if (root == null) {
                try {
                    root = FXMLLoader.load(getClass().getResource("banking.fxml"));
                    System.out.println("Loaded banking.fxml");
                } catch (Exception e) {
                    System.out.println("Could not load banking.fxml: " + e.getMessage());
                }
            }
            
            if (root != null) {
                stage.setScene(new Scene(root, 800, 600));
                stage.setTitle("Dashboard - " + username);
                stage.centerOnScreen();
            } else {
                showError("Dashboard screen not found. Please create Banking.fxml or dashboard.fxml in the same directory as your class files.");
            }
            
        } catch (Exception e) {
            // Changed from IOException to Exception to catch any unexpected errors
            showError("Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}