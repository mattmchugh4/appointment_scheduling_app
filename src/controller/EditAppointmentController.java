package controller;
/**
 * The AddAppointmentForm class is a controller for the AddCustomerForm view.
 * It handles the user inputs when the "create new appointment" option is selected. A new appointment can either be saved,
 * or the process can be canceled without saving changes.
 *
 * @author Matt McHugh
 *
 */
import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import utilities.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class EditAppointmentController implements Initializable {
    public TextField titleInput;
    public TextField descriptionInput;
    public TextField locationInput;
    public TextField typeInput;
    public ComboBox customerBox;
    public ComboBox userBox;
    public ComboBox contactBox;
    public DatePicker appointmentDate;
    public ComboBox startHour;
    public ComboBox startMinute;
    public ComboBox endHour;
    public ComboBox endMinute;
    public Text systemMessageText;
    public TextField appointmentIDInput;
    private Parent scene;

    /**
     * This method initialize the form. It creates the combo boxes for users, contacts and customers. It also creates the
     * hours and minutes combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            startHour.setItems(Utility.HOURS);
            endHour.setItems(Utility.HOURS);
            startMinute.setItems(Utility.MINUTES);
            endMinute.setItems(Utility.MINUTES);

            ObservableList<String> allContacts = FXCollections.observableArrayList();
            String contactStatement = "SELECT Contact_Name FROM contacts";
            ResultSet contactResult = Query.run(contactStatement);
            while (contactResult.next()) {
                String contactName = contactResult.getString("Contact_Name");
                allContacts.add(contactName);
            }
            contactBox.setItems(allContacts);
//            contactBox.setValue("Li Lee");

            ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
            String customerStatement = "SELECT Customer_Name FROM customers";
            ResultSet customerResult = Query.run(customerStatement);
            while (customerResult.next()) {
                String customerName = customerResult.getString("Customer_Name");
                allCustomerNames.add(customerName);
            }
            customerBox.setItems(allCustomerNames);
//            customerBox.setValue("blah");

            ObservableList<String> allUsers = FXCollections.observableArrayList();
            String userStatement = "SELECT User_Name FROM users";
            ResultSet userResult = Query.run(userStatement);
            while (userResult.next()) {
                String userName = userResult.getString("User_Name");
                allUsers.add(userName);
            }
            userBox.setItems(allUsers);
//            userBox.setValue("test");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getAppointment(Appointment appointmentToModify) throws SQLException {
        int appointmentID = appointmentToModify.getAppointmentID();
        appointmentIDInput.setText(String.valueOf(appointmentID));
        titleInput.setText(appointmentToModify.getTitle());
        descriptionInput.setText(appointmentToModify.getDescription());
        locationInput.setText(appointmentToModify.getLocation());
        typeInput.setText(appointmentToModify.getType());
        customerBox.setValue(appointmentToModify.getCustomerName());
        userBox.setValue(appointmentToModify.getUserName());
        contactBox.setValue(appointmentToModify.getContactName());

        LocalDateTime appointmentStartDateTime = appointmentToModify.getLocalStartDateTime();
        LocalDateTime appointmentEndDateTime = appointmentToModify.getLocalEndDateTime();
        appointmentDate.setValue(appointmentStartDateTime.toLocalDate());
        startHour.setValue(Integer.toString(appointmentStartDateTime.getHour()));
        startMinute.setValue(Integer.toString(appointmentStartDateTime.getMinute()));
        endHour.setValue(Integer.toString(appointmentEndDateTime.getHour()));
        endMinute.setValue(Integer.toString(appointmentEndDateTime.getMinute()));

    }


    /**
     * This method handles the event when the save button is clicked and it inserts a new appointment into the database.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        String newTitle = titleInput.getText();
        String newDescription = descriptionInput.getText();
        String newLocation = locationInput.getText();
        String newType = typeInput.getText();
        String newCustomerName = (String) customerBox.getValue();
        String newUserName = (String) userBox.getValue();
        String newContactName = (String) contactBox.getValue();
        LocalDate newAppointmentDate = appointmentDate.getValue();
        String newStartHour = (String) startHour.getValue();
        String newStartMinute = (String) startMinute.getValue();
        String newEndHour = (String) endHour.getValue();
        String newEndMinute = (String) endMinute.getValue();
        int appointmentID =  Integer.parseInt(appointmentIDInput.getText());

        if (newTitle == null || newDescription == null || newLocation == null || newType == null || newCustomerName == null ||
                newUserName == null || newContactName == null || newAppointmentDate == null || newStartHour == null ||
                newStartMinute == null || newEndHour == null || newEndMinute == null) {
            Utility.setErrorMessage(systemMessageText, "You must enter valid values for all fields to save a new appointment.");
            return;
        }
        int newContactID = Utility.getContactID(newContactName);
        int newUserID = Utility.getUserID(newUserName);
        int newCustomerID = Utility.getCustomerID(newCustomerName);

        LocalDateTime newLocalStartTime = LocalDateTime.of(newAppointmentDate, LocalTime.of(Integer.parseInt(newStartHour), Integer.parseInt(newStartMinute)));
        Timestamp newStart = Timestamp.valueOf(newLocalStartTime);
        LocalDateTime newStartUTCTime = Utility.convertTimeToUTC(newLocalStartTime);

        LocalDateTime newLocalEndTime = LocalDateTime.of(newAppointmentDate, LocalTime.of(Integer.parseInt(newEndHour), Integer.parseInt(newEndMinute)));
        Timestamp newEnd = Timestamp.valueOf(newLocalEndTime);
        LocalDateTime newEndUTCTime = Utility.convertTimeToUTC(newLocalEndTime);

        if (!newLocalEndTime.isAfter(newLocalStartTime)) {
            Utility.setErrorMessage(systemMessageText, "Appointment end time must be after start time.");
            return;
        }
        if(!Utility.isWithinBusinessHours(newStartUTCTime) || !Utility.isWithinBusinessHours(newEndUTCTime)) {
            Utility.setErrorMessage(systemMessageText, "Appointment time must be within 8:00 a.m. to 10:00 p.m. EST.");
            return;
        }
        if(Utility.hasConflict(newLocalStartTime, newLocalEndTime, newCustomerID, appointmentID)) {
            Utility.setErrorMessage(systemMessageText, "Appointment can not conflict with an existing appointment with a customer.");
            return;
        }
        String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        Query.run(updateStatement,newTitle, newDescription, newLocation, newType, newStart, newEnd, newCustomerID, newUserID, newContactID, appointmentID);

        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        newStage.setTitle("Appointments");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    /**
     * This method handles the event when the cancel button is clicked. The view is closed without saving changes.
     *
     * @param actionEvent ActionEvent
     * @throws IOException Input/Output Exception
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        newStage.setTitle("Appointments");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
