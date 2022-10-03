package datasource;

import gatewayDTOs.Element;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementGatewayTest {

    @Test
    public void createElementTable() {
        ElementTableGateway.createTable();
    }

    @Test
    public void testCreateElement() throws SQLException {
        Element Carbon = new Element(16, 2.32);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(16, 2.32);

        assertEquals(Carbon.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
    }

    @Test
    public void testPersistElement() throws SQLException {
        Element element = new Element(24, 23.3);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(24, 23.3);
        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);

        elementGateway.elementDTO.setAtomicMass(4.11);
        element.setAtomicMass(4.11);
        elementGateway.persist();

        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
    }

    @Test
    public void testDeleteElement() throws SQLException {
        Element element = new Element(3, 1.8);
        ElementTableGateway elementGateway = ElementTableGateway.createElement(3, 1.8);
        assertTrue(elementGateway.delete());
    }
}
