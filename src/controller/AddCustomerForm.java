package controller;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.net.URL;
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

    public void onSaveButton(ActionEvent actionEvent) throws IOException {

        String newName = nameInput.getText();
        String newAddress = addressInput.getText();
        String newZip = zipInput.getText();
        String newPhone = phoneInput.getText();
        String newState = (String) stateBox.getValue();

        if(newName.isEmpty() || newAddress.isEmpty() || newZip.isEmpty() || newPhone.isEmpty() || newState.isEmpty()) {
            systemMessageText.setText("You must enter values for all fields to save a new customer.");
            systemMessageText.setFill(Color.RED);
            AnchorPane.setLeftAnchor(systemMessageText, systemMessageText.getParent().getBoundsInLocal().getWidth() / 2);
            AnchorPane.setRightAnchor(systemMessageText, systemMessageText.getParent().getBoundsInLocal().getWidth() / 2);
            systemMessageText.setVisible(true);
            return;
        }

        System.out.println(newAddress + newName + newState + newPhone + newZip);

        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPageForm.fxml"));
        newStage.setTitle("Customer Page");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
