package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ViewAppointmentsController implements Initializable {

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
    private Parent scene;


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

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
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
}
