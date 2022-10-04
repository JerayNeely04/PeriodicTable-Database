package datasource;

import gatewayDTOs.Metal;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MetalGatewayTest {

    @Test
    public void createMetalTable() { MetalTableGateway.createTable(); }

    @Test
    public void testCreateMetal() throws SQLException
    {
        MetalTableGateway metalGateway = new MetalTableGateway(1);

        assertEquals(1, metalGateway.getDissolvedBy());
    }

    @Test
    public void testFindDissolvedBy() throws SQLException {
        MetalTableGateway metalGateway = new MetalTableGateway(1);
        Metal findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());
    }

    @Test
    public void testFindAllMetal() throws SQLException {
        // Recreate table
        MetalTableGateway.createTable();

        MetalTableGateway metalGateway = new MetalTableGateway(1);
        metalGateway.insertRow(2);
        metalGateway.insertRow(3);
        metalGateway.insertRow(4);

        ArrayList<Metal> allMetalRecords = MetalTableGateway.findAll();

        assertNotNull(allMetalRecords);
        assertEquals(4, allMetalRecords.size());
    }

    @Test
    public void testPersistMetal() throws SQLException {
        MetalTableGateway metalGateway = new MetalTableGateway(1);
        assertEquals(1, metalGateway.getDissolvedBy());
        metalGateway.persist();

        Metal findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());
    }

    @Test
    public void testDeleteMetal() throws SQLException {
        MetalTableGateway metalGateway = new MetalTableGateway(1);
        Metal findMetal = MetalTableGateway.findDissolvedBy(1);
        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());

        metalGateway.delete();
        findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNull(findMetal);
    }
}