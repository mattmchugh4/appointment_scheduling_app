package controller;
/**
 * The LoginController class is a controller for the login page.
 * It handles the user inputs when logging in and displays an error message or brings the user to the first page of the
 * application based on the entered inputs.
 *
 * @author Matt McHugh
 *
 */
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
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {
    public Label UserLocationLabel;
    public TextField PasswordTextInput;
    public TextField UsernameTextInput;
    public Text systemMessageText;
    public Label usernameStatic;
    public Label passwordStatic;
    public Button loginStatic;
    public Text locationStaticText;
    public Text applicationLoginStatic;
    private Parent scene;
    private ResourceBundle resourceBundle;
    private String errorMessage = "Invalid Username/Password";

    /**
     * This method initializes the login page and handles translation of the page into french based on the users local language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TimeZone userTimeZone = TimeZone.getDefault();
        ZoneId userZone = userTimeZone.toZoneId();
        UserLocationLabel.setText(userZone.toString());

        Locale userLocale = Locale.getDefault();
        if (userLocale.getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("LoginController", userLocale);
            locationStaticText.setText(rb.getString("Location"));
            usernameStatic.setText(rb.getString("Username"));
            passwordStatic.setText(rb.getString("Password"));
            loginStatic.setText(rb.getString("Login"));
            applicationLoginStatic.setText(rb.getString("Application"));
            errorMessage = rb.getString("Error");
        }
    }

    /**
     *This method handles the user clicking the login button. It either displays an error message if the
     * wrong username/password is entered or it brings the user to the first page of the application.
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
                Utility.userLoginName = username;
                bufferedWriter.newLine();
                String successfulAttempt = "Login attempt successful";
                bufferedWriter.write(successfulAttempt);
                bufferedWriter.close();
                Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/LoginMessageForm.fxml"));
                loginPageStage.setTitle("Application Page");
                loginPageStage.setScene(new Scene(scene));
                loginPageStage.show();
                return;
            }
        }
        bufferedWriter.newLine();
        String failedAttempt = "Login attempt failed";
        bufferedWriter.write(failedAttempt);
        Utility.setErrorMessage(systemMessageText, errorMessage);
        bufferedWriter.close();
    }
}
