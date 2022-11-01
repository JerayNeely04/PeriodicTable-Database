package datasource;

import DomainModel.Mapper.ElementNotFoundException;
import gatewayDTOs.ElementDTO;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementGatewayTest {

    private Connection conn = DatabaseConnection.getInstance().getConnection();

    /**
     * Testing if we can create an Element in the Element table.
     * @throws SQLException if we cannot create the Element.
     */
    @AfterEach
    public void rollback() throws SQLException {
        conn.rollback();
    }

    @BeforeEach
    public void setAutoCommitToFalse() throws SQLException {
        conn.setAutoCommit(false);
    }

    @Test
    public void testCreateElement() throws SQLException {
        ElementDTO Carbon = new ElementDTO (11, 6, 6.2);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(11, 6,6.2);

        assertEquals(Carbon.getAtomicMass(), elementGateway.getAtomicMass(), 0.001);
    }

    /**
     * Testing the update method.
     * @throws SQLException SQL Exception if we cannot update to the database.
     */
    @Test
    public void testPersistElement() throws SQLException {
        ElementDTO element = new ElementDTO(4, 3,3.3);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(4, 3,3.3);
        assertEquals(element.getAtomicMass(), elementGateway.getAtomicMass(), 0.001);

        elementGateway.setAtomicMass(8.6);
        elementGateway.persist();

        assertEquals(8.6, elementGateway.getAtomicMass(), 0.001);
    }

    /**
     * Testing the delete method.
     * @throws DataException SQL Exception if we cannot delete from the database.
     */
    @Test
    public void testDeleteElement() throws DataException, ElementNotFoundException {
        ElementDTO element = new ElementDTO(3, 1,1.8);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(3, 1, 1.8);
        assertTrue(elementGateway.delete());
    }

    /**
     * Testing the find method
     * @throws DataException SQL exception if we cannot find an Element.
     */
    @Test
    public void testFindByAtomicNum() throws DataException, ElementNotFoundException {
        ElementTableGateway gateway = ElementTableGateway.createElement(1,24,25.5);
        ElementDTO element = ElementTableGateway.findByAtomicNumber(24);
        assertEquals(25.5, element.getAtomicMass(), 0.001);
    }

    /**
     * Testing the findAll method that returns all the elements.
     * @throws DataException if we cannot return all the elements.
     */
    @Test
    public void testFindAll() throws DataException {
        ArrayList<ElementDTO> elements = ElementTableGateway.findAll();
        assertEquals(0, elements.size());           /* we have rollback() */
    }
}
