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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    public static LocalDateTime convertTimeToUTC(LocalDateTime localDateTime) {
        ZonedDateTime newZonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        ZonedDateTime newUTZonedCDateTime = newZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime newUTCTime = newUTZonedCDateTime.toLocalDateTime();
        return newUTCTime;
    }
    public static LocalDateTime convertTimeToLocal(LocalDateTime utcDateTime) {
        ZonedDateTime utcZonedDateTime = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        return localDateTime;
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
            LocalDateTime utcStart = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime utcEnd = result.getTimestamp("End").toLocalDateTime();
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = Utility.getContactName(contactID);
            String userName = Utility.getUserName(userID);
            String customerName = Utility.getCustomerName(customerID);
            LocalDateTime localStart = Utility.convertTimeToLocal(utcStart);
            LocalDateTime localEnd = Utility.convertTimeToLocal(utcEnd);

            System.out.println(result.getTimestamp("Start"));
            System.out.println(appointmentID);
            System.out.println(utcStart);
            System.out.println(localStart);


            Appointment newAppointment = new Appointment(appointmentID, title, description, utcStart, utcEnd,
                    customerID, contactID, location, type, userID, contactName, userName, customerName, localStart, localEnd);

            allAppointments.add(newAppointment);

        }
        return allAppointments;
    }

    /**
     * This method is used to convert contact ID to contact name.
     * It takes an int id  and returns a string name.
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static String getContactName(int contactID) throws SQLException {
        String contactName = null;
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        ResultSet stateResult = Query.run(sql, contactID);
        if (stateResult.next()) {
            contactName = stateResult.getString("Contact_Name");
        }
        return contactName;
    }
    /**
     * This method is used to convert contact name to contact ID.
     * It takes a string name and returns an int id.
     * @param contactName
     * @return
     * @throws SQLException
     */
    public static int getContactID(String contactName) throws SQLException {
        int contactID = -1;
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        ResultSet stateResult = Query.run(sql, contactName);
        if (stateResult.next()) {
            contactID = stateResult.getInt("Contact_ID");
        }
        return contactID;
    }

    /**
     * This method is used to convert user ID to user name.
     * It takes an int id  and returns a string name.
     * @param userID
     * @return
     * @throws SQLException
     */
    public static String getUserName(int userID) throws SQLException {
        String userName = null;
        String sql = "SELECT User_Name FROM users WHERE User_ID = ?";
        ResultSet stateResult = Query.run(sql, userID);
        if (stateResult.next()) {
            userName = stateResult.getString("User_Name");
        }
        return userName;
    }
    /**
     * This method is used to convert user name to user ID.
     * It takes a string name and returns an int id.
     * @param userName
     * @return
     * @throws SQLException
     */
    public static int getUserID(String userName) throws SQLException {
        int userID = -1;
        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";
        ResultSet stateResult = Query.run(sql, userName);
        if (stateResult.next()) {
            userID = stateResult.getInt("User_ID");
        }
        return userID;
    }

    /**
     * This method is used to convert customer ID to customer name.
     * It takes an int id  and returns a string name.
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static String getCustomerName(int customerID) throws SQLException {
        String customerName = null;
        String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";
        ResultSet stateResult = Query.run(sql, customerID);
        if (stateResult.next()) {
            customerName = stateResult.getString("Customer_Name");
        }
        return customerName;
    }
    /**
     * This method is used to convert customer name to customer ID.
     * It takes a string name and returns an int id.
     * @param customerName
     * @return
     * @throws SQLException
     */
    public static int getCustomerID(String customerName) throws SQLException {
        int customerID = -1;
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = ?";
        ResultSet stateResult = Query.run(sql, customerName);
        if (stateResult.next()) {
            customerID = stateResult.getInt("Customer_ID");
        }
        return customerID;
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
