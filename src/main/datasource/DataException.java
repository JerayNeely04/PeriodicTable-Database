package datasource;

import java.sql.SQLException;

public class DataException extends SQLException {
    public DataException(String errorMsg) {
        System.out.println(errorMsg);
    }

    public DataException(String errorMsg, SQLException e) {
        System.out.println(errorMsg + ", " + e.getMessage());
    }
}
