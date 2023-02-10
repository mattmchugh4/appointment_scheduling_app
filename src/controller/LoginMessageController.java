package controller;

import dao.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoginMessageController implements Initializable{
    public Text dateTimeMessage;
    public Text welcomeMessage;
    public Text upcomingMessage;
    public Text appointmentDetails;
    private Parent scene;

    public void onClickApplication(ActionEvent actionEvent) throws IOException {
        Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        loginPageStage.setTitle("Appointments");
        loginPageStage.setScene(new Scene(scene));
        loginPageStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String userName = Utility.userLoginName;
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime futureDateTime = currentDateTime.plusMinutes(15);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");
            String formatedDateTime = currentDateTime.format(formatter);
            welcomeMessage.setText("Welcome " + userName);
            dateTimeMessage.setText("The current date and time is " + formatedDateTime);
            int userID = -1;

            String idStatement = "SELECT User_ID FROM users WHERE User_Name = ?";
            ResultSet idResult = Query.run(idStatement, userName);
            if(idResult.next()){
                userID = idResult.getInt("User_ID");
            }
            String sqlStatement = "SELECT * FROM appointments WHERE User_ID = ?";
            ResultSet result = Query.run(sqlStatement, userID);
            while(result.next()) {
                LocalDateTime appointmentTime = result.getTimestamp("Start").toLocalDateTime();
                if (appointmentTime.isAfter(currentDateTime) && appointmentTime.isBefore(futureDateTime) && appointmentTime.toLocalDate().equals(currentDateTime.toLocalDate())) {
                    String appointmentID = Integer.toString(result.getInt("Appointment_ID"));
                    String formattedScheduledTime = appointmentTime.format(formatter);
                    upcomingMessage.setText("You have an appointment scheduled to begin within 15 minutes.");
                    appointmentDetails.setText("Appointment ID " + appointmentID + " is scheduled to begin at " + formattedScheduledTime);
                    appointmentDetails.setVisible(true);
                }
            }

        } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
    }
}
