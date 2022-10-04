package datasource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseGatewayTest {
    /**
     * Tests to make sure a base can be created
     */
    @Test
    public void testCreateBase() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();

        // Create new base
        BaseGateway baseGateway = new BaseGateway("Lithium hydroxide", 1);

        assertEquals(1, baseGateway.getId());
        assertEquals("Lithium hydroxide", baseGateway.getName());
        assertEquals(1, baseGateway.getSolute());
    }

    /**
     * Tests to make sure a base can be deleted
     */
    @Test(expected = DataException.class)
    public void testDeleteBase() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();

        // Create new base
        BaseGateway baseGateway = new BaseGateway("Lithium hydroxide", 1);

        // Equals tests
        assertEquals(1, baseGateway.getId());
        assertEquals("Lithium hydroxide", baseGateway.getName());
        assertEquals(1, baseGateway.getSolute());

        // Delete test
        baseGateway.delete();
        new BaseGateway(1);
    }

    @Test
    public void testUpdateBase() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();

        // Create new base
        BaseGateway baseGateway = new BaseGateway("Lithium hydroxide", 1);

        // Equals tests
        assertEquals(1, baseGateway.getId());
        assertEquals("Lithium hydroxide", baseGateway.getName());
        assertEquals(1, baseGateway.getSolute());

        // Change solute id
        baseGateway.setSolute(2);
        assertEquals(2, baseGateway.getSolute());

        assertTrue(baseGateway.update());
    }

    @Test(expected = DataException.class)
    public void testParentRowDeleted() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();
        CompoundGateway.createTable();

        // Create compound and base
        CompoundGateway compoundGateway = new CompoundGateway("Water");
        BaseGateway baseGateway = new BaseGateway("Lithium hydroxide", 1);

        // Test for correct IDs
        assertEquals(1, compoundGateway.getId());
        assertEquals(2, baseGateway.getId());

        // Delete row from the compound table
        compoundGateway.delete();

        // Try to find the row in the base table
        new BaseGateway(2);
    }
}
