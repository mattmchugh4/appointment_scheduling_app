package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class Query {

    private static Statement stmt;

    public static void select() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String customerName = rs.getString("Customer_Name");
        }
    }



    public static ResultSet makeQuery(String query){
        ResultSet result = null;
        try{
            stmt = JDBC.connection.createStatement();
            if(query.toLowerCase().startsWith("select"))
                result = stmt.executeQuery(query);
            if(query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(query);
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return result;
    }
}
