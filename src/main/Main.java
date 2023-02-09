package main;


import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {
    public static void main(String[] args) {
        JDBC.openConnection();
//        Locale.setDefault(new Locale("fr"));
        launch(args);
        JDBC.closeConnection();
    }


    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        stage.setTitle("First Screen");
//        stage.setScene(new Scene(root, 600, 400));
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


}
