package controller;
/**
 * The AppointmentViewController class is a controller for the appointments view.
 * It handles the user inputs and displays appointments to the UI.
 *
 * @author Matt McHugh
 *
 */
import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentViewController implements Initializable {

    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> locationColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, String> contactColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    public TableColumn<Appointment, String> customerNameColumn;
    @FXML
    public TableColumn<Appointment, String> userNameColumn;
    public Text systemMessageText;
    public RadioButton allAppointments;
    public RadioButton weekRadio;
    public RadioButton monthRadio;
    private Parent scene;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Initialize appointment table and populates it with information from all appointments.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("formattedLocalStartTimeString"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("formattedLocalEndTimeString"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appointments.addAll(Utility.getAllAppointments());
            appointmentTable.setItems(appointments);
            allAppointments.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method `onAddAppointment` opens the Add Appointment Form when the "Add" button is clicked.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentForm.fxml"));
        newStage.setTitle("Add Appointment");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    /**
     * The method `onEditAppointment` opens the Edit Form when the "Edit" button is clicked.
     * Before opening the form, it checks that an appointment has been selected. If an appointment is selected,
     * it retrieves the selected appointment and passes it to the Edit Appointment Form.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onEditAppointment(ActionEvent actionEvent) throws IOException {

        try {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader editAppointmentLoader = new FXMLLoader();
            editAppointmentLoader.setLocation(getClass().getResource("/view/EditAppointmentForm.fxml"));
            editAppointmentLoader.load();
            scene = editAppointmentLoader.getRoot();
            EditAppointmentController editAppointmentController = editAppointmentLoader.getController();
            editAppointmentController.getAppointment(selectedAppointment);

            newStage.setTitle("Edit Appointment");
            newStage.setScene(new Scene(scene));
            newStage.show();

        } catch (NullPointerException | SQLException e) {
            Utility.setErrorMessage(systemMessageText, "You must select an appointment to edit.");
        }
    }

    /**
     * The method `onDeleteAppointment` deletes the selected appointment when the "Delete" button is clicked.
     * Before deleting the appointment, it prompts the user to confirm the deletion.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws IOException {
        try {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            String appointmentID = Integer.toString(selectedAppointment.getAppointmentID());
            String appointmentType = selectedAppointment.getType();

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Confirm Delete");
            confirmDelete.setContentText("Appointment ID: " + appointmentID + " | Appointment Type: " + appointmentType + "\nAre you sure you want to delete this appointment?");

            ButtonType confirmDeleteButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDelete.getButtonTypes().setAll(confirmDeleteButton, cancelButton);
            Optional<ButtonType> result = confirmDelete.showAndWait();

            if (result.get() == confirmDeleteButton){
                String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
                Query.run(deleteStatement, appointmentID);
                appointments.remove(selectedAppointment);
                appointmentTable.setItems(appointments);
                Utility.setSystemMessage(systemMessageText, "Appointment ID " + appointmentID + ", Type " + appointmentType + ", was deleted.");
            } else {
                Utility.setSystemMessage(systemMessageText, "Appointment was not deleted.");
            }
        } catch (NullPointerException | SQLException e) {
            Utility.setErrorMessage(systemMessageText, "You must select an appointment to delete.");
        }
    }
    /**
     *
     *  The onViewCustomers method is a event handler that handles the button press event to view the customers.
     *  @param actionEvent
     *  @throws IOException
     */
    public void onViewCustomers(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerViewForm.fxml"));
        newStage.setTitle("Customers");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    /**
     * This method is called when the user clicks the "All Appointments" button. It retrieves all the appointments
     * from the database and displays them in the appointments table.
     * @param actionEvent
     * @throws Exception
     */
    public void onSelectAllAppointments(ActionEvent actionEvent) throws Exception {
        appointments.clear();
        appointments.addAll(Utility.getAllAppointments());
        appointmentTable.setItems(appointments);
    }

    /**
     * This method is called when the user clicks the "Week View" button. It retrieves all the appointments
     * from the database that fall within the current week and displays them in the appointments table.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSelectWeek(ActionEvent actionEvent) throws SQLException {
        appointments.clear();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneWeekLater = currentDateTime.plus(1, ChronoUnit.WEEKS);

        String sqlStatement = "SELECT * FROM appointments WHERE Start >= ? AND End <= ?";
        ResultSet result = Query.run(sqlStatement, Timestamp.valueOf(currentDateTime), Timestamp.valueOf(oneWeekLater));

        while(result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            String location = result.getString("Location");
            int userID = result.getInt("User_ID");

            LocalDateTime localStartDateTime = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime localEndDateTime = result.getTimestamp("End").toLocalDateTime();

            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = Utility.getContactName(contactID);
            String userName = Utility.getUserName(userID);
            String customerName = Utility.getCustomerName(customerID);

            Appointment newAppointment = new Appointment(appointmentID, title, description, localStartDateTime, localEndDateTime,
                    customerID, contactID, location, type, userID, contactName, userName, customerName);

            appointments.add(newAppointment);
        }
        appointmentTable.setItems(appointments);
    }

    /**
     * This method is called when the user clicks the "Month View" button. It retrieves all the appointments
     * from the database that fall within the current month and displays them in the appointments table.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSelectMonth(ActionEvent actionEvent) throws SQLException {

        appointments.clear();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneWeekLater = currentDateTime.plus(1, ChronoUnit.MONTHS);

        String sqlStatement = "SELECT * FROM appointments WHERE Start >= ? AND End <= ?";
        ResultSet result = Query.run(sqlStatement, Timestamp.valueOf(currentDateTime), Timestamp.valueOf(oneWeekLater));

        while(result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            String location = result.getString("Location");
            int userID = result.getInt("User_ID");

            LocalDateTime localStartDateTime = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime localEndDateTime = result.getTimestamp("End").toLocalDateTime();

            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = Utility.getContactName(contactID);
            String userName = Utility.getUserName(userID);
            String customerName = Utility.getCustomerName(customerID);

            Appointment newAppointment = new Appointment(appointmentID, title, description, localStartDateTime, localEndDateTime,
                    customerID, contactID, location, type, userID, contactName, userName, customerName);

            appointments.add(newAppointment);
        }
        appointmentTable.setItems(appointments);
    }

    /**
     *  The onclickReport method is a event handler that handles the button press event to view reports. It opens the
     *  reports page.
     * @param actionEvent
     * @throws IOException
     */
    public void onClickReport(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsForm.fxml"));
        newStage.setTitle("Reports");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
