// src/AlertUtil.java
import javafx.scene.control.Alert;

public class AlertUtil {
    public static void show(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static void info(String msg) { show("Info", msg, Alert.AlertType.INFORMATION); }
    public static void error(String msg) { show("Error", msg, Alert.AlertType.ERROR); }
}