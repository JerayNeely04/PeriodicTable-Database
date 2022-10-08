package datasource;

import gatewayDTOs.BaseDTO;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseGatewayTest {
    /**
     * Tests to make sure a base can be created
     * @throws DataException the database exception
     */
    @Test
    public void testCreateConstructor() throws DataException {
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
     * Tests to make sure the gateway can find a base by id
     * @throws DataException the database exception
     */
    @Test
    public void testFindConstructor() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();

        // Create new base
        new BaseGateway("Lithium hydroxide", 1);

        // Find that row
        BaseGateway gateway = new BaseGateway(1);

        // Equals tests
        assertEquals(1, gateway.getId());
        assertEquals("Lithium hydroxide", gateway.getName());
        assertEquals(1, gateway.getSolute());
    }

    /**
     * Tests to make sure a base can be deleted
     * @throws DataException the database exception
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

    /**
     * Tests to make sure a base row can be updated
     * @throws DataException the database exception
     */
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

    /**
     * Tests to make sure the TDG can return all rows by solute
     * @throws DataException the database exception
     */
    @Test
    public void testTDGFindById() throws DataException {
        // Resets
        KeyRowDataGateway.reset();
        BaseGateway.createTable();

        ArrayList<String> namesToCheck = new ArrayList<String>();
        namesToCheck.add("Lithium hydroxide");
        namesToCheck.add("Sodium hydroxide");
        namesToCheck.add("Potassium hydroxide");
        namesToCheck.add("Calcium hydroxide");

        // Add a new base to the table
        new BaseGateway("Lithium hydroxide", 1);
        new BaseGateway("Sodium hydroxide", 1);
        new BaseGateway("Potassium hydroxide", 1);
        new BaseGateway("Calcium hydroxide", 1);

        // Try to retrieve that row
        ArrayList<BaseDTO> basesList = BaseGateway.findAllBasesSoluableBy(1);

        // Equals checks
        assert basesList != null;
        for (int i = 0; i < namesToCheck.size(); i++) {
            assertEquals(namesToCheck.get(i), basesList.get(i).getName());
        }
    }

    /**
     * Tests to make sure the foreign key dependencies are deleted properly
     * @throws DataException the database exception
     */
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
