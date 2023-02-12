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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * The LoginMessageController class is responsible for displaying messages to the user after logging in,
 * such as the current date and time, welcome message, upcoming appointments,
 * and appointment details.
 *
 * @author Matt McHugh
 *
 */
public class LoginMessageController implements Initializable{
    public Text dateTimeMessage;
    public Text welcomeMessage;
    public Text upcomingMessage;
    public Text appointmentDetails;
    private Parent scene;

    /**
     * The onClickApplication method switches the current page to the ViewAppointmentsForm page.
     * @param actionEvent
     * @throws IOException
     */
    public void onClickApplication(ActionEvent actionEvent) throws IOException {
        Stage loginPageStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        loginPageStage.setTitle("Appointments");
        loginPageStage.setScene(new Scene(scene));
        loginPageStage.show();
    }

    /**
     * The initialize method initializes the messages displayed to the user after logging in.
     * It retrieves the user's login name, calculates the current date and time,
     * retrieves the user's appointments from the database, and checks if any appointments
     * are scheduled to begin within the next 15 minutes. If there is, the method displays
     * an upcoming appointment message and the details of the appointment.
     *
     * @param url
     * @param resourceBundle
     */
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
