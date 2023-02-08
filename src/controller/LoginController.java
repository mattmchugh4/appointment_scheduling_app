package controller;

import dao.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label UserLocationLabel;
    public TextField PasswordTextInput;
    public TextField UsernameTextInput;
    public Text systemMessageText;
    private Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnClIckLoginButton(ActionEvent actionEvent) throws IOException, SQLException {

        String username = UsernameTextInput.getText();
        String password = PasswordTextInput.getText();
        String logLine = "Username: " + username + " attempted to log in at " + new Date();
        String breakLine = "-----------------------------------------------------------------------------------------------------------";

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.newLine();
        bufferedWriter.write(breakLine);
        bufferedWriter.newLine();
        bufferedWriter.write(logLine);

        String getPassword = "SELECT Password FROM users WHERE User_Name = ?";
        ResultSet stateResult = Query.run(getPassword, username);

        if (stateResult.next()) {
            String returnedPassword = stateResult.getString("Password");
            if (returnedPassword.equals(password)) {
                bufferedWriter.newLine();
                String attemptLine = "Login attempt successful";
                bufferedWriter.write(attemptLine);
                bufferedWriter.close();
                Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
                loginPageStage.setTitle("Application Page");
                loginPageStage.setScene(new Scene(scene));
                loginPageStage.show();
                return;
            }
        }
        bufferedWriter.newLine();
        String attemptLine = "Login attempt failed";
        bufferedWriter.write(attemptLine);
        Utility.setErrorMessage(systemMessageText, "Invalid Username/Password");
        bufferedWriter.close();
    }
}
