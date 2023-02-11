package dao;
/**
 * The query class provides a method to run SQL statements on the database.
 *
 * @author Matt McHugh
 *
 */
import java.sql.*;


public abstract class Query {
    /**
     *  Runs a SQL statement on the database using the specified parameters.
     * @param sql
     * @param values
     * @return
     * @throws SQLException
     */
    public static ResultSet run(String sql, Object... values) throws SQLException {
        ResultSet result = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value instanceof String) {
                ps.setString(i + 1, (String) value);
            } else if (value instanceof Integer) {
                ps.setInt(i + 1, (Integer) value);
            } else if(value instanceof Timestamp) {
                ps.setTimestamp(i + 1, (Timestamp) value);
            }
        }
        if(sql.toLowerCase().startsWith("select")) {
            result = ps.executeQuery();
        }
        if(sql.toLowerCase().startsWith("delete") || sql.toLowerCase().startsWith("insert") || sql.toLowerCase().startsWith("update")) {
            ps.executeUpdate();
        }
        return result;
    }
}
