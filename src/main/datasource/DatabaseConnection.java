package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection databaseSingleton;
    private Connection connection = null;
    private Boolean SHOULD_COMMIT = false;
    private  static final String DATABASE = "swe400_41";
    public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";
    public static final String LOGIN_NAME = "swe400_4";
    public static final String PASSWORD = "pwd4swe400_4F22";

    /**
     * @return the only instance of the connection
     */
    public static DatabaseConnection getInstance() {
        if (databaseSingleton == null) {
            databaseSingleton = new DatabaseConnection();
        }
        return databaseSingleton;
    }

    /**
     * private constructor to create the singleton
     */
    private DatabaseConnection() {}

    /**
     * Tries to create and return a new connection to the datbase
     * @return the database connection
     */
    public Connection getConnection() {
        if (connection == null) {
            // driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Unable to load the class");
                System.exit(-1);
            }

            // connection
            try {
                connection = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
                connection.setAutoCommit(SHOULD_COMMIT);

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
}