package datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu/swe400-41?useTimezone=true&serverTimezone=UTC";
    public static final String LOGIN_NAME = "swe400_4";
    public static final String PASSWORD = "pwd4swe400_4F22";
    protected Connection m_dbConn = null;

    public DatabaseConnection() throws SQLException
    {
        try
        {
            m_dbConn = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
            DatabaseMetaData meta = m_dbConn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return m_dbConn;
    }

    public boolean activateJDBC()
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }

        return true;
    }
}
