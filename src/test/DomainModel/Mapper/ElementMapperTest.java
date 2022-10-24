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

    /**
     * Tests to make sure an element can be created from the mappers constructor
     * @throws SQLException
     */
    @Test
    public void CreateElementTest() throws SQLException {
        conn.setAutoCommit(false);
        ElementMapper mapper = new ElementMapper("Carbon", 1, 2.2);
        Element element = mapper.getMyElement();

        assertEquals("Carbon", element.getName());
        assertEquals(1, element.getAtomicNumber());
        assertEquals(2.2, element.getAtomicMass(), 0.0001);
        conn.rollback();
    }

    /**
     * Tests to make sure the finder constructor works
     * @throws SQLException
     */
    @Test
    public void testFindElement() throws SQLException {
        ElementMapper mapper = new ElementMapper("Carbon");
        Element element = mapper.getMyElement();

        assertEquals("Carbon", element.getName());
        assertEquals(1, element.getAtomicNumber());
        assertEquals(2.2, element.getAtomicMass(), 0.0001);
    }
}
