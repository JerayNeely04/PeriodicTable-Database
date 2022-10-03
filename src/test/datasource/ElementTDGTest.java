package datasource;

import gatewayDTOs.ElementDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementTDGTest {
    /**
     * Tests to make sure the table data gateway can create a new row and fetch that
     * row by ID and return a DTO
     */
    @Test
    public void testFindingAnElementByID()
    {
        // Reset the id key and element table
        KeyRDG.reset();
        ElementTDG.createTable();

        // Get the only instance of the table data gateway
        ElementTDG gateway = ElementTDG.getInstance();

        // Add a new row
        gateway.createRow("Oxygen", 8, 15.999);

        // Get that rows DTO
        ElementDTO oxygenDTO = gateway.findByAtomicNumber(8);

        // Equals tests
        assertEquals(1, oxygenDTO.getId());
        assertEquals("Oxygen", oxygenDTO.getName());
        assertEquals(8, oxygenDTO.getAtomicNum());
        assertEquals(15.999, oxygenDTO.getAtomicMass(), 0.0001);
    }

    /**
     * Tests to make sure an element row can be found by name
     */
    @Test
    public void testFindingAnElementByName()
    {
        // Reset the id key and element table
        KeyRDG.reset();
        ElementTDG.createTable();

        // Get the only instance of the table data gateway
        ElementTDG gateway = ElementTDG.getInstance();

        // Add a new row
        gateway.createRow("Hydrogen", 1, 1.0078);

        // Get that rows DTO
        ElementDTO hydrogenDTO = gateway.findByName("Hydrogen");

        // Equals tests
        assertEquals(1, hydrogenDTO.getId());
        assertEquals("Hydrogen", hydrogenDTO.getName());
        assertEquals(1, hydrogenDTO.getAtomicNum());
        assertEquals(1.0078, hydrogenDTO.getAtomicMass(), 0.0001);
    }
}
