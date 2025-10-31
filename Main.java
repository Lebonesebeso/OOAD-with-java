// Main.java

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.File;
 
public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception {

        // Method 3: Use File for absolute paths

        File fxmlFile = new File("C:/Users/Admin/Desktop/BankingApplicationSystem/fxml/Login.fxml");

        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());

        Parent root = loader.load();

        primaryStage.setTitle("Bonz Bank - Login");

        primaryStage.setScene(new Scene(root, 400, 400));

        primaryStage.setResizable(false);

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
 