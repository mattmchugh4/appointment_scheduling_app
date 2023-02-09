package utilities;

import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Appointment;
import model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class Utility {
        public static final ObservableList<String> HOURS = FXCollections.observableArrayList();
        public static final ObservableList<String> MINUTES = FXCollections.observableArrayList();
        static {
            for (int i = 1; i <= 24; i++) {
                HOURS.add(String.valueOf(i));
            }
            for (int i = 0; i <= 59; i++) {
                MINUTES.add(String.format("%02d", i));
            }
        }
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customers";
        ResultSet result = Query.run(sqlStatement);
        while(result.next()){
            int id = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String phone = result.getString("Phone");
            String zip = result.getString("Postal_Code");
            int division = result.getInt("Division_ID");
            Customer newCustomer = new Customer(id, name, address, zip, phone, division);
            allCustomers.add(newCustomer);
        }
        return allCustomers;
    }
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments";
        ResultSet result = Query.run(sqlStatement);
        while(result.next()){
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String type = result.getString("Type");
            String location = result.getString("Location");
            int userID = result.getInt("User_ID");
            Timestamp start = result.getTimestamp("Start");
            Timestamp end = result.getTimestamp("End");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = Utility.getContactName(contactID);

            Appointment newAppointment = new Appointment(appointmentID, title, description, start, end, customerID, contactID, location, type, userID, contactName);
            allAppointments.add(newAppointment);

        }
        return allAppointments;
    }
    public static String getContactName(int contactID) throws SQLException {
        String contactName = null;
        String getDivision = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        ResultSet stateResult = Query.run(getDivision, contactID);
        if (stateResult.next()) {
            contactName = stateResult.getString("Contact_Name");
        }
        return contactName;
    }

    public static void setErrorMessage(Text textObject,String message) {
        textObject.setText(message);
        textObject.setFill(Color.RED);
        textObject.setFont(new Font(16));

        AnchorPane parent = (AnchorPane) textObject.getParent();
        parent.setLeftAnchor(textObject, parent.getWidth() / 2 - textObject.getLayoutBounds().getWidth() / 2);
        parent.setRightAnchor(textObject, parent.getWidth() / 2 + textObject.getLayoutBounds().getWidth() / 2);
        textObject.setVisible(true);
    }
}
