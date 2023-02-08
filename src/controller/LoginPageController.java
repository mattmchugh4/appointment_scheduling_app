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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import utilities.Utility;

import java.io.IOException;
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
//
//        try {
//            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
//
//            ModifyPartFormController modPartController  = modifyPartLoader.getController();
//            modPartController.getPart(selectedPart);
//
//            Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//            scene = FXMLLoader.load(getClass().getResource("/view/EditCustomerForm.fxml"));
//            newStage.setTitle("Edit Customer");
//            newStage.setScene(new Scene(scene));
//            newStage.show();
//        } catch (NullPointerException e) {
//            Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
//            noPartSelected.setTitle("Error Dialog");
//            noPartSelected.setContentText("You must select a part to modify.");
//            noPartSelected.showAndWait();
//        }
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        newStage.setTitle("Add Customer");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    public void onAddAppointment(ActionEvent actionEvent) {
    }
}
