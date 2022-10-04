package datasource;

import java.sql.SQLException;

public class DataException extends SQLException {

    public DataException(String errMsg){
        System.out.println(errMsg);
    }

    public DataException(String errMsg, SQLException e) {
        System.out.println(errMsg + " " + e);
    }
}
