package DomainModel.Mapper;

import DomainModel.Element;
import datasource.ChemicalGatewayTest;
import datasource.ChemicalTableGateway;
import datasource.DataException;
import datasource.DatabaseConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ElementMapperTest {

    private Connection conn = DatabaseConnection.getInstance().getConnection();

    @Test
    public void CreateElementTest() throws SQLException {
        conn.setAutoCommit(false);
        ElementMapper mapper = new ElementMapper("Lead", 1, 2.2);
        Element element = mapper.getMyElement();

        assertEquals("Lead", element.getName());
        assertEquals(1, element.getAtomicNumber());
        assertEquals(2.2, element.getAtomicMass(), 0.0001);
        conn.rollback();
    }
}
