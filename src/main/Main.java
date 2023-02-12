package main;

import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class is the main.java file for the program. It initializes the application and calls the login form view.
 *
 * @author Matt McHugh
 *
 */
public class Main extends Application {
    /**
     * The main method opens a JDBC connection, launches the application, and closes the JDBC connection.
     *
     * @param args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }


    /**
     * The start method is called when the application is launched.
     * It sets the title of the stage, creates a new scene, and loads the LoginForm.fxml file.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
