package controller;

import dao.Query;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * The CustomerViewController class is a controller for the customers view.
 * It handles the user inputs  It handles the user inputs and displays customer information to the UI.
 *
 * @author Matt McHugh
 *
 */
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
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * Initialize customer table and populates it with information from all customers.
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

            customers.addAll(Utility.getAllCustomers());

            customerTable.setItems(customers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method `onDeleteCustomer` deletes the selected appointment when the "Delete" button is clicked.
     * Before deleting the appointment, it prompts the user to confirm the deletion.
     *
     * @param actionEvent
     */
    public void onDeleteCustomer(ActionEvent actionEvent) {
        try {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            String customerID = Integer.toString(selectedCustomer.getId());

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Confirm Delete");
            confirmDelete.setContentText("Customer ID: " + customerID + "\nAre you sure you want to delete this customer?" +
                    "\nThis will also delete all appointments with this customer.");

            ButtonType confirmDeleteButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDelete.getButtonTypes().setAll(confirmDeleteButton, cancelButton);
            Optional<ButtonType> result = confirmDelete.showAndWait();

            if (result.get() == confirmDeleteButton){

                String findAppointments = "DELETE FROM appointments WHERE Customer_ID = ?";
                Query.run(findAppointments, customerID);
                String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
                Query.run(deleteStatement, customerID);
                customers.remove(selectedCustomer);
                customerTable.setItems(customers);
                Utility.setSystemMessage(systemMessageText, "Customer ID " + customerID + ", was deleted.");
            } else {
                Utility.setSystemMessage(systemMessageText, "Customer was not deleted.");
            }
        } catch (NullPointerException | SQLException e) {
            Utility.setErrorMessage(systemMessageText, "You must select a customer to delete.");
        }
    }

    /**
     * The onEditCustomer method handles the event when a user click the edit button. It passes the selected customer to
     * the EditCustomerController and opens the view or displays an error message if no customer is selected.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * The method `onAddCustomer` opens the Add Customer Form when the "Add" button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        newStage.setTitle("Add Customer");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    /**
     * This method handles the event when the customer clicks the appointments button. It opens the appointments view.
     * @param actionEvent
     * @throws IOException
     */
    public void onViewAppointment(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        newStage.setTitle("Appointments");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
