package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class Query {


    public static ResultSet run(String sql, Object... values) throws SQLException {
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value instanceof String) {
                ps.setString(i + 1, (String) value);
            } else if (value instanceof Integer) {
                ps.setInt(i + 1, (Integer) value);
            }
        }
        return ps.executeQuery();
    }
}

