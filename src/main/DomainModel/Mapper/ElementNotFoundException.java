package DomainModel.Mapper;

import java.sql.SQLException;

public class ElementNotFoundException extends SQLException {
    public ElementNotFoundException(String e) {
        System.out.println(e);
    }
}
