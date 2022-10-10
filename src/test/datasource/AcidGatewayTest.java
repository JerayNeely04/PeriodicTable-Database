package datasource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcidGatewayTest {
    @Test
    public void testCreateConstructor() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        AcidGateway.createTable();

        // Create new element row data gateway
        AcidGateway gateway = new AcidGateway("hydrochloric", 1);

        // Equals tests
        assertEquals(1, gateway.getId());
        assertEquals("hydrochloric", gateway.getName());
        assertEquals("1", gateway.getSolute());

    }

    @Test
    public void testFindConstructor() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        AcidGateway.createTable();

        // Create an element row
        new AcidGateway("hydrochloric", 1);

        // Find that row
        AcidGateway foundAcidGateway = new AcidGateway(1);

        // Equals tests
        assertEquals(1, foundAcidGateway.getId());
        assertEquals("hydrochloric", foundAcidGateway.getName());
        assertEquals("1", foundAcidGateway.getSolute());

    }

    @Test
    public void testDeleteAcid() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        AcidGateway.createTable();

        // Create new element row data gateway
        AcidGateway gateway = new AcidGateway("hydrochloric", 1);

        // Created tests
        assertEquals("hydrochloric", gateway.getName());
        assertEquals(1, gateway.getSolute());

        // Deleted test
        assertTrue(gateway.delete());
    }

    @Test
    public void testUpdatingElement() throws DataException {
        // Reset the id key and element table
        KeyRowDataGateway.reset();
        AcidGateway.createTable();

        // Create new element row data gateway
        AcidGateway gateway = new AcidGateway("hydrochloric", 1);

        // Created tests
        assertEquals("hydrochloric", gateway.getName());
        assertEquals(1, gateway.getSolute());

        // Fix the atomic number and test
        gateway.setSolute(1);
        assertEquals(1, gateway.getSolute());

        // Update test
        assertTrue(gateway.update());
    }
}
