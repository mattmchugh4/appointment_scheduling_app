package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label UserLocationLabel;
    public TextField PasswordTextInput;
    public TextField UsernameTextInput;
    private Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnClIckLoginButton(ActionEvent actionEvent) throws IOException {
        Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        loginPageStage.setTitle("Application Page");
        loginPageStage.setScene(new Scene(scene));
        loginPageStage.show();
    }
}
