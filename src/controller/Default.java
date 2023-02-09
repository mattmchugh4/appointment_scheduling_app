package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Default {
    private Parent scene;

    public void onClickCustomer(ActionEvent actionEvent) throws IOException {
        Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        loginPageStage.setTitle("Application Page");
        loginPageStage.setScene(new Scene(scene));
        loginPageStage.show();
    }

    public void onClickApplication(ActionEvent actionEvent) throws IOException {
        Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentForm.fxml"));
        loginPageStage.setTitle("Application Page");
        loginPageStage.setScene(new Scene(scene));
        loginPageStage.show();
    }
}
