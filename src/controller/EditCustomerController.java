package controller;

import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Customer;
import utilities.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * The EditCustomerController class is a controller for the EditCustomerForm view.
 * It handles the user inputs when the "edit customer" option is selected. A new customer can either be saved,
 * or the process can be canceled without saving changes.
 *
 * @author Matt McHugh
 *
 */
public class EditCustomerController implements Initializable {
    public TextField nameInput;
    public ComboBox countryBox;
    public TextField addressInput;
    public ComboBox stateBox;
    public TextField zipInput;
    public TextField phoneInput;
    public Text systemMessageText;
    public TextField customerIDInput;
    private Parent scene;
    private int customerID;
    private static final String[] US_States = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
            "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
            "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
            "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
            "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
            "New Hampshire", "New Jersey", "New Mexico", "New York",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
            "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
            "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
            "West Virginia", "Wisconsin", "Wyoming", "District of Columbia"
    };
    private static final String[] UK_States = {
            "England", "Scotland", "Wales", "Northern Ireland"
    };
    private static final String[] Canada_States = {
            "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
            "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island",
            "Quebec", "Saskatchewan", "Yukon"
    };

    /**
     * This method is used as a controller to pass the customer selected to edit into this class. The information from
     * the passed customer is used to populate the form text fields.
     * @param customerToModify
     * @throws SQLException
     */
    public void getCustomer(Customer customerToModify) throws SQLException {
        customerID = customerToModify.getId();
        customerIDInput.setText(String.valueOf(customerID));
        nameInput.setText(customerToModify.getName());
        addressInput.setText(customerToModify.getAddress());
        zipInput.setText(customerToModify.getZip());
        phoneInput.setText(customerToModify.getPhone());
        int countryID = -1;

        String getCoutryStatement = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        ResultSet countryResult = Query.run(getCoutryStatement, customerToModify.getDivision());
        if (countryResult.next()) {
            countryID = countryResult.getInt("Country_ID");
        }
        switch (countryID) {
            case 1:
                countryBox.setValue("U.S.");
                break;
            case 2:
                countryBox.setValue("UK");
                break;
            case 3:
                countryBox.setValue("Canada");
                break;
        }
        String getDivisionStatement = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        ResultSet divisionResult = Query.run(getDivisionStatement, customerToModify.getDivision());
        if (divisionResult.next()) {
            stateBox.setValue(divisionResult.getString("Division"));
        }
    }
    /**
     * Initialize the combo box of countries and creates the listener to set the list for the division combo box when
     * the country changes. The lambda expression in this code provides a concise way to add a listener to the form elements.
     * This improves the readability of the code and would make it easier to maintain.
     *
     * @param url            URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList("U.S.", "UK", "Canada");
        countryBox.setItems(countries);

        countryBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("U.S.")) {
                stateBox.setItems(FXCollections.observableArrayList(US_States));
            } else if (newValue.equals("UK")) {
                stateBox.setItems(FXCollections.observableArrayList(UK_States));
            } else if (newValue.equals("Canada")) {
                stateBox.setItems(FXCollections.observableArrayList(Canada_States));
            };
        });
    }
    /**
     * Handles the event when the save button is clicked. If the inputs are valid, the change is saved and the user is
     * returned out of the edit form. If the inputs are not valid, an error message is shown to the user and no changes
     * are saved.
     *
     * @param actionEvent ActionEvent
     * @throws IOException Input/Output Exception
     * @throws SQLException SQL Exception
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException, SQLException {

        String newName = nameInput.getText();
        String newAddress = addressInput.getText();
        String newZip = zipInput.getText();
        String newPhone = phoneInput.getText();
        String newState = (String) stateBox.getValue();
        int newDivision = -1;

        if(newName.isEmpty() || newAddress.isEmpty() || newZip.isEmpty() || newPhone.isEmpty() || newState.isEmpty()) {
            Utility.setErrorMessage(systemMessageText, "You must enter valid values for all fields to save a change to a customer.");
            return;
        }

        String getDivision = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        ResultSet stateResult = Query.run(getDivision, newState);
        if (stateResult.next()) {
            newDivision = stateResult.getInt("Division_ID");
        }

        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        Query.run(updateStatement,newName, newAddress, newZip, newPhone, newDivision, customerID);

        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerViewForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
    /**
     * Handles the event when the cancel button is clicked. The view is closed without saving changes.
     *
     * @param actionEvent ActionEvent
     * @throws IOException Input/Output Exception
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerViewForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
