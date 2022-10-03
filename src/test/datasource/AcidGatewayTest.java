package datasource;

import gatewayDTOs.Acid;
import java.util.ArrayList;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AcidGatewayTest {
    @Test
    public void testCreateTable() {
        tableAcidGateway.createTable();
    }

    @Test
    public void testCreateAcid() throws SQLException {
        tableAcidGateway acidGate = new tableAcidGateway(1);

        assertEquals(1, acidGate.getSolute());
    }

    @Test
    public void testFindBySolute() throws SQLException {
        tableAcidGateway acidGateway = new tableAcidGateway(20);
        Acid findAcid = tableAcidGateway.findBySolute(20);

        assertNotNull(findAcid);
        assertEquals(20, findAcid.getSolute());
    }

    @Test
    public void testFindAll() throws SQLException {
        // Recreate table
        tableAcidGateway.createTable();

        tableAcidGateway acidGateway = new tableAcidGateway(5);
        acidGateway.insertRow(10);
        acidGateway.insertRow(15);
        acidGateway.insertRow(20);

        ArrayList<Acid> allAcidRecords = tableAcidGateway.findAll();

        assertNotNull(allAcidRecords);
        assertEquals(4, allAcidRecords.size());
    }

    @Test
    public void testPersist() throws SQLException {
        tableAcidGateway acidGateway = new tableAcidGateway(30);
        assertEquals(30, acidGateway.getSolute());
        acidGateway.persist();

        Acid findAcid = tableAcidGateway.findBySolute(30);

        assertNotNull(findAcid);
        assertEquals(30, findAcid.getSolute());
    }

    @Test
    public void testDelete() throws SQLException {
        tableAcidGateway acidGateway = new tableAcidGateway(50);
        Acid findAcid = tableAcidGateway.findBySolute(50);
        assertNotNull(findAcid);
        assertEquals(50, findAcid.getSolute());

        acidGateway.delete();
        findAcid = tableAcidGateway.findBySolute(50);

        assertNull(findAcid);
    }
}