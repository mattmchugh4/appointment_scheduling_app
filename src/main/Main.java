package main;


import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        System.out.println("Hello world!");
    }


    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        JDBC.openConnection();
    }


}
