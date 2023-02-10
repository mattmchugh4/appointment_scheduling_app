package controller;

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
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    private Parent scene;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();


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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentForm.fxml"));
        newStage.setTitle("Add Appointment");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

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
                Utility.setSystemMessage(systemMessageText, "Appointment number " + appointmentID + " was deleted.");
            } else {
                    Utility.setSystemMessage(systemMessageText, "Appointment was not deleted.");
                }
        } catch (NullPointerException | SQLException e) {
            Utility.setErrorMessage(systemMessageText, "You must select an appointment to delete.");
        }
    }

    public void onViewCustomers(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerViewForm.fxml"));
        newStage.setTitle("Customers");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
