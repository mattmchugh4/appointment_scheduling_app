package main;


import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.TimeZone;


public class Main extends Application {
    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getDefault());
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
//        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 600, 400));
//        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


}
