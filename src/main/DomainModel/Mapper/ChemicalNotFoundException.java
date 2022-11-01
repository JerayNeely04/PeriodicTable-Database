package DomainModel.Mapper;

import java.sql.SQLException;

public class ChemicalNotFoundException extends SQLException {
    /**
     * The exception constructor that prints the error message!
     * @param errMsg the specified error message.
     */
    public ChemicalNotFoundException(String errMsg){
        System.out.println(errMsg);
    }

    /**
     * The second constructor that prints the error message with the raised exception.
     * @param errMsg the specified error message.
     * @param e the raised exception.
     */
    public ChemicalNotFoundException(String errMsg, SQLException e) {
        System.out.println(errMsg + " " + e);
    }
}
