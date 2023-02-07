package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import utilities.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    public TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    public TableColumn<Customer, String> customerNameColumn;

    @FXML
    public TableColumn<Customer, String> customerAddressColumn;

    @FXML
    public TableColumn<Customer, String> customerZipColumn;

    @FXML
    public TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    public TableColumn<Customer, Integer> customerDivisionColumn;

    @FXML
    public TableView<Customer> customerTable;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerZipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
            customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

            ObservableList<Customer> customers = FXCollections.observableArrayList();
            customers.addAll(Utility.getAllCustomers());

            customerTable.setItems(customers);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
