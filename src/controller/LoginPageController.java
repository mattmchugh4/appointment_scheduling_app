package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    public TableColumn customerIDColumn;
    public TableColumn customeNameColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerZipColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerDivisionColumn;
    public TableView customerTable;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onUpdateCustomer(ActionEvent actionEvent) {
    }

    public void onAddCustomer(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) {
    }
}
