package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {

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
    public Text systemMessageText;

    private Parent scene;

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

    public void onEditCustomer(ActionEvent actionEvent) throws IOException {
        try {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader editCustomerLoader = new FXMLLoader();
            editCustomerLoader.setLocation(getClass().getResource("/view/EditCustomerForm.fxml"));
            editCustomerLoader.load();
            scene = editCustomerLoader.getRoot();
            EditCustomerController editCustomerController = editCustomerLoader.getController();
            editCustomerController.getCustomer(selectedCustomer);

            newStage.setTitle("Edit Customer");
            newStage.setScene(new Scene(scene));
            newStage.show();

        } catch (NullPointerException | SQLException e) {
            Utility.setErrorMessage(systemMessageText, "You must select a customer to edit.");
        }
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        newStage.setTitle("Add Customer");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    public void onViewAppointment(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        newStage.setTitle("Appointments");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
