package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerForm implements Initializable {
    public TextField nameInput;
    public ComboBox countryBox;
    public TextField addressInput;
    public ComboBox stateBox;
    public TextField zipInput;
    public TextField phoneInput;
    public Text systemMessageText;
    private Parent scene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onSaveButton(ActionEvent actionEvent) {
    }

    public void onCancelButton(ActionEvent actionEvent) {
    }
}
