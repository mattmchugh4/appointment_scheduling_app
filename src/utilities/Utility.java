package utilities;

import dao.JDBC;
import dao.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Utility {

    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM CUSTOMERS";
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
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM CUSTOMERS";
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
