/**
 * The AddCustomerForm class is a controller for the AddCustomerForm view.
 * It handles the user inputs when the "create new customer" option is selected. A new customer can either be saved,
 * or the process can be canceled without saving changes.
 *
 * @author Matt McHugh
 *
 */

package controller;

import dao.JDBC;
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
import utilities.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerForm implements Initializable {
    public TextField nameInput;
    public ComboBox countryBox;
    public TextField addressInput;
    public ComboBox stateBox;
    public TextField zipInput;
    public TextField phoneInput;
    public Text systemMessageText;
    private Parent scene;
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
     * Initialize the combo box of countries and creates the listener to set the list for the division combo box when the country changes.
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
     * Handle the event when the save button is clicked and inserts a new customer into the database.
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
            Utility.setErrorMessage(systemMessageText, "You must enter valid values for all fields to save a new customer.");
            return;
        }
        String getDivision = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        ResultSet stateResult = Query.run(getDivision, newState);
        if (stateResult.next()) {
            newDivision = stateResult.getInt("Division_ID");
        }

        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, newName);
        ps.setString(2, newAddress);
        ps.setString(3, newZip);
        ps.setString(4, newPhone);
        ps.setInt(5, newDivision);
        ps.executeUpdate();

        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
    /**
     * Handle the event when the cancel button is clicked. The view is closed without saving changes.
     *
     * @param actionEvent ActionEvent
     * @throws IOException Input/Output Exception
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
