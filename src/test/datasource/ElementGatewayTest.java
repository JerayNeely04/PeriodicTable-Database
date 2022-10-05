package datasource;

import gatewayDTOs.Element;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementGatewayTest {

    /**
     * Creates the Element table in the database.
     * @throws DataException if we cannot create the table.
     */
    @Test
    public void createElementTable() throws DataException {
        ElementTableGateway.createTable();
    }

    /**
     * testing if we can create an Element in the Element table.
     * @throws DataException if we cannot create the Element.
     */
    @Test
    public void testCreateElement() throws DataException {
        Element Carbon = new Element(16, 2.32);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(16, 2.32);

        assertEquals(Carbon.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
    }

    /**
     * Testing the update method.
     * @throws DataException SQL Exception if we cannot update to the database.
     */
    @Test
    public void testPersistElement() throws DataException {
        Element element = new Element(24, 23.3);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(24, 23.3);
        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);

        elementGateway.elementDTO.setAtomicMass(4.11);
        element.setAtomicMass(4.11);
        elementGateway.persist();

        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
    }

    /**
     * Testing the delete method.
     * @throws DataException SQL Exception if we cannot delete from the database.
     */
    @Test
    public void testDeleteElement() throws DataException {
        Element element = new Element(3, 1.8);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(3, 1.8);
        assertTrue(elementGateway.delete());
    }

    /**
     * Testing the find method
     * @throws DataException SQL exception if we cannot find an Element.
     */
    @Test
    public void testFindByAtomicNum() throws DataException {
        ElementTableGateway elementTableGateway = ElementTableGateway.findByAtomicNumber(24);
        assertEquals(4.11, elementTableGateway.elementDTO.getAtomicMass(), 0.001);
    }

    /**
     * Testing the findAll method that returns all the elements.
     * @throws DataException if cannot return all the elements.
     */
    @Test
    public void testFindAll() throws DataException {
        ArrayList<Element> elements = ElementTableGateway.findAll();
        assertEquals(2, elements.size());
    }
}
