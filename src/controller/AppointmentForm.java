package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentForm implements Initializable {
    public TableView appointmentTable;
//    @FXML
//    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn appointmentIDColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn typeColumn;
    public TableColumn contactColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIDColumn;
    public TableColumn userIDColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
