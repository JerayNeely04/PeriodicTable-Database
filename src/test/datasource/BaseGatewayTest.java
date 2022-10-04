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
        ElementGateway.createTable();
        BaseGateway.createTable();

        // Create element to use as a solute (temp)
        ElementGateway elementGateway = new ElementGateway("TempSolute", 1, 1.0);

        // Create new base
        BaseGateway baseGateway = new BaseGateway("Lithium hydroxide", 1);

        // Instance variable tests
        assertEquals("TempSolute", elementGateway.getName());
        assertEquals(1, elementGateway.getAtomicNum());
        assertEquals(1.0, elementGateway.getAtomicMass(), 0.0001);

        assertEquals("Lithium hydroxide", baseGateway.getName());
        assertEquals(1, baseGateway.getSolute());

        // Globally unique ids test
        assertEquals(1, elementGateway.getId());
        assertEquals(2, baseGateway.getId());
    }

    /**
     * Tests to make sure a base can be deleted
     */
    @Test
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
        assertTrue(baseGateway.delete());
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
