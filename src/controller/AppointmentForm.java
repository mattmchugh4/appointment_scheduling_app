package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import utilities.Utility;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AppointmentForm implements Initializable {

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
    public TableColumn<Appointment, Timestamp> startColumn;
    @FXML
    public TableColumn<Appointment, Timestamp> endColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    public TableColumn<Appointment, Integer> userIDColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));


            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            appointments.addAll(Utility.getAllAppointments());

            appointmentTable.setItems(appointments);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
