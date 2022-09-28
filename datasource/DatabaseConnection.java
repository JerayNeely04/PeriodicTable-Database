package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection databaseSingleton;
    private Connection connection = null;
    public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu/swe400-41";
    public static final String LOGIN_NAME = "swe400_4";
    public static final String PASSWORD = "pwd4swe400_4F22";

    public static DatabaseConnection getInstance() {
        if (databaseSingleton == null) {
            databaseSingleton = new DatabaseConnection();
        }
        return databaseSingleton;
    }

    private DatabaseConnection() {}

    public Connection getConnection() {
        if (connection == null) {
            // driver
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Unable to load the class. Terminating the program");
                System.exit(-1);
            }

            // connection
            try {
                connection = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
                return connection;
            } catch (SQLException e) {
                System.out.println("Error getting connection: " + e.getMessage());
                System.exit(-1);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(-1);
            }
        }

        return connection;
    }

    public boolean activateJDBC() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;
    }
}
