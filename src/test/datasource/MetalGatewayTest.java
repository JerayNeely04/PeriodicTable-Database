package datasource;

import gatewayDTOs.MetalDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MetalGatewayTest {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    /**
     * Testing if we can create Metal in the Metal Table
     * @throws SQLException if we cannot create Table
     */
    @Test
    public void testCreateMetal() throws SQLException
    {
        conn.setAutoCommit(false);

        MetalTableGateway metalGateway = new MetalTableGateway(1);

        assertEquals(1, metalGateway.getDissolvedBy());

        conn.rollback();
    }

    /**
     * Testing the find method
     * @throws SQLException
     */
    @Test
    public void testFindDissolvedBy() throws SQLException {
        conn.setAutoCommit(false);

        MetalTableGateway metalGateway = new MetalTableGateway(1);
        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());
        conn.rollback();
    }

    /**
     * Testing the find all method
     * @throws SQLException if we cannot return all the Metal
     */
    @Test
    public void testFindAllMetal() throws SQLException {
        conn.setAutoCommit(false);

        MetalTableGateway metalGateway = new MetalTableGateway(1);
        metalGateway.insertRow(5);
        metalGateway.insertRow(8);
        metalGateway.insertRow(10);

        ArrayList<MetalDTO> allMetalRecords = MetalTableGateway.findAll();

        assertNotNull(allMetalRecords);
        assertEquals(4, allMetalRecords.size());
        conn.rollback();
    }

    /**
     * Testing the update method
     * @throws SQLException if we cannot update the database
     */
    @Test
    public void testPersistMetal() throws SQLException {
        MetalTableGateway metalGateway = new MetalTableGateway(1);
        assertEquals(1, metalGateway.getDissolvedBy());
        metalGateway.persist();

        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());
        conn.rollback();
    }

    /**
     * Testing the delete method
     * @throws SQLException if we cannot delete from the database
     */
    @Test
    public void testDeleteMetal() throws SQLException {
        MetalTableGateway metalGateway = new MetalTableGateway(1);
        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(1);
        assertNotNull(findMetal);
        assertEquals(1, findMetal.getDissolvedBy());

        metalGateway.delete();
        findMetal = MetalTableGateway.findDissolvedBy(1);

        assertNull(findMetal);
    }
}