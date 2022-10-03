package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyRowDataGateway {

    /**
     * creates a new key table and adds the globally unique key tracker row
     */
    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS KeyTable";
        String createStatement = "CREATE TABLE KeyTable (id BIGINT PRIMARY KEY)";
        String keyStatement = "INSERT INTO KeyTable (id) VALUES (?)";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();

            // This table will only have 1 row and 1 col, so we can create it when the table is created
            stmt = conn.prepareStatement(keyStatement);
            stmt.setLong(1, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the currently stored key by one and updates the row
     * @return a new unused id which should be globally unique
     */
    public static long generateId()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM KeyTable";
        String update = "UPDATE KeyTable SET id = ?";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            long currentKey = results.getLong("id");
            long nextKey = currentKey + 1;

            stmt = conn.prepareStatement(update);
            stmt.setLong(1, nextKey);
            stmt.executeUpdate();

            return nextKey;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to generate new ID");
            return 0;
        }
    }

    /**
     * resets the row storing the current id to 0
     * @return whether the reset was done
     */
    public static boolean reset()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String updateStatement = "UPDATE KeyTable SET id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(updateStatement);
            stmt.setLong(1, 0);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to reset id col");

            return false;
        }
    }
}
