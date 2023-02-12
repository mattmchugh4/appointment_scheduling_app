package controller;

import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import utilities.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
/**
 * The ReportsController class is a controller for the reports view.
 * It handles and displays reports based on user inputs.
 *
 * @author Matt McHugh
 *
 */
public class ReportsController implements Initializable {

    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> locationColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, String> contactColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    public TableColumn<Appointment, String> customerNameColumn;
    @FXML
    public TableColumn<Appointment, String> userNameColumn;
    public Text systemMessageText;
    public Text countryAnswer;
    public ComboBox countryBox;
    public Text monthTypeAnswer;
    public ComboBox typeBox;
    public ComboBox monthBox;
    public ComboBox contactBox;
    private Parent scene;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * The initialize method is called when the page is initialized. It is used to initialize the combo boxes and tableview
     * for the form.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> countries = FXCollections.observableArrayList("U.S.", "UK", "Canada");
            countryBox.setItems(countries);

            ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
                    "May", "June", "July", "August", "September", "October", "November", "December");
            monthBox.setItems(months);

            ObservableList<String> allContacts = FXCollections.observableArrayList();
            String contactStatement = "SELECT Contact_Name FROM contacts";
            ResultSet contactResult = Query.run(contactStatement);
            while (contactResult.next()) {
                String contactName = contactResult.getString("Contact_Name");
                allContacts.add(contactName);
            }
            contactBox.setItems(allContacts);

            ObservableList<String> allTypes = FXCollections.observableArrayList();
            Set<String> uniqueTypes = new HashSet<>();

            String typeStatement = "SELECT Type FROM Appointments";
            ResultSet typeResult = Query.run(typeStatement);
            while (typeResult.next()) {
                String typeString = typeResult.getString("Type");
                uniqueTypes.add(typeString);
            }
            typeBox.setItems(FXCollections.observableArrayList(uniqueTypes));


            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("formattedLocalStartTimeString"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("formattedLocalEndTimeString"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onContactChange(ActionEvent actionEvent) throws SQLException {
        appointments.clear();
        String selectedContactName = (String) contactBox.getValue();
        int selectedContactID = Utility.getContactID(selectedContactName);

        String sqlStatement = "SELECT * FROM appointments WHERE Contact_ID = ?";
        ResultSet result = Query.run(sqlStatement, selectedContactID);

        while(result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            String location = result.getString("Location");
            int userID = result.getInt("User_ID");

            LocalDateTime localStartDateTime = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime localEndDateTime = result.getTimestamp("End").toLocalDateTime();

            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = Utility.getContactName(contactID);
            String userName = Utility.getUserName(userID);
            String customerName = Utility.getCustomerName(customerID);

            Appointment newAppointment = new Appointment(appointmentID, title, description, localStartDateTime, localEndDateTime,
                    customerID, contactID, location, type, userID, contactName, userName, customerName);

            appointments.add(newAppointment);
        }
        appointmentTable.setItems(appointments);
    }

    /**
     * This method handles the event when the customer clicks the appointments button. It opens the appointments view.
     * @param actionEvent
     * @throws IOException
     */
    public void onViewAppointments(ActionEvent actionEvent) throws IOException {
        Stage newStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        newStage.setTitle("Appointments");
        newStage.setScene(new Scene(scene));
        newStage.show();
    }

    /**
     * This method handles the event when the user changes the country and is used to create the number of customers by
     * country report.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCountryChange(ActionEvent actionEvent) throws SQLException {
        String countryName = (String) countryBox.getValue();
        int count = 0;
        int countryId = -1;
        if (countryName.equals("U.S.")) {
            countryId = 1;
        } else if (countryName.equals("UK")) {
            countryId = 2;
        } else if (countryName.equals("Canada")) {
            countryId = 3;
        };
        String sqlStatement = "SELECT * FROM customers";
        ResultSet result = Query.run(sqlStatement);
        while(result.next()) {
            int divisionID = result.getInt("Division_ID");
            String countryStatement = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
            ResultSet countryResult = Query.run(countryStatement, divisionID);
            if(countryResult.next()) {
                if(countryResult.getInt("Country_ID") == countryId){
                    count += 1;
                }
            }
        }
        countryAnswer.setText(Integer.toString(count));
        countryAnswer.setVisible(true);
    }

    /**
     * This method handles the event when the user clicks the submit button. It is used to create the Appointments by
     * type/month report.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSubmit(ActionEvent actionEvent) throws SQLException {
        String type = (String) typeBox.getValue();
        String month = (String) monthBox.getValue();
        int count = 0;

        int intMonth = 0;
        switch(month) {
            case "January":
                intMonth = 1;
                break;
            case "February":
                intMonth = 2;
                break;
            case "March":
                intMonth = 3;
                break;
            case "April":
                intMonth = 4;
                break;
            case "May":
                intMonth = 5;
                break;
            case "June":
                intMonth = 6;
                break;
            case "July":
                intMonth = 7;
                break;
            case "August":
                intMonth = 8;
                break;
            case "September":
                intMonth = 9;
                break;
            case "October":
                intMonth = 10;
                break;
            case "November":
                intMonth = 11;
                break;
            case "December":
                intMonth = 12;
                break;
        }

        String sqlStatement = "SELECT * FROM Appointments";
        ResultSet result = Query.run(sqlStatement);
        while(result.next()) {
            LocalDateTime dateTime = result.getTimestamp("Start").toLocalDateTime();
            String returnedType = result.getString("Type");
            int returnedMonth = dateTime.getMonth().getValue();
            if(returnedMonth == intMonth && type.equals(returnedType)){
                count += 1;
            }
        }
        monthTypeAnswer.setText(Integer.toString(count));
        monthTypeAnswer.setVisible(true);
    }
}
