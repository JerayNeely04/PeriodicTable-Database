package datasource;

import gatewayDTOs.CompoundDTO;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompoundGatewayTest {

    @Test
    public void testCreateConstructor() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create new element row data gateway
        CompoundGateway gateway = new CompoundGateway("Water");

        // Equals tests
        assertEquals(1, gateway.getId());
        assertEquals("Water", gateway.getName());
    }

    @Test
    public void testFindConstructor() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create an element row
        new CompoundGateway("Sugar");

        // Find that row
        CompoundGateway foundCompoundGateway = new CompoundGateway(1);

        // Equals tests
        assertEquals(1, foundCompoundGateway.getId());
        assertEquals("Sugar", foundCompoundGateway.getName());

    }

    @Test(expected = DataException.class)
    public void testDeleteElement() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create new element row data gateway
        CompoundGateway gateway = new CompoundGateway("Carbon Dioxide");

        // Created tests
        assertEquals(1, gateway.getId());
        assertEquals("Carbon Dioxide", gateway.getName());

        // Deleted test
        gateway.delete();
        new CompoundGateway(1);
    }

    @Test
    public void testUpdatingElement() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create new element row data gateway
        CompoundGateway gateway = new CompoundGateway("Salt");

        // Created tests
        assertEquals(1, gateway.getId());
        assertEquals("Salt", gateway.getName());

        gateway.setName("Water");
        assertEquals("Water", gateway.getName());

        // Update test
        assertTrue(gateway.update());
    }

    @Test
    public void testCorrectID() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create elements
        CompoundGateway saltGateway = new CompoundGateway("Salt");
        CompoundGateway waterGateway = new CompoundGateway("water");

        // Test ID increment
        assertEquals(1, saltGateway.getId());
        assertEquals(2, waterGateway.getId());
    }

    @Test
    public void testFindingAnElementByName() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create an element row
        new CompoundGateway("Salt");

        // Get that rows DTO
        CompoundDTO saltDTO= CompoundGateway.findByName("Salt");

        // Equals tests
        assert saltDTO != null;
        assertEquals(1, saltDTO.getId());
        assertEquals("Salt", saltDTO.getName());
    }

    @Test
    public void testGettingAllRows() throws DataException
    {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        CompoundGateway.createTable();

        // Create elements
        new CompoundGateway("Carbon Dioxide");
        new CompoundGateway("Sugar");
        new CompoundGateway("Salt");
        new CompoundGateway("Water");


        // Get all rows from element table
        ArrayList<CompoundDTO> compoundList = CompoundGateway.findAll();
        int idToCheck = 0;

        // Check to make sure each element DTO has the correct ID
        assert compoundList != null;
        for (CompoundDTO compound : compoundList) {
            idToCheck++;
            assertEquals(idToCheck, compound.getId());
        }

        // 5 element DTOs were checked in total
        assertEquals(compoundList.size(), 4);
    }

}
