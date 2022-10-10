package datasource;

import gatewayDTOs.Acid;
import java.util.ArrayList;
import java.sql.Connection;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AcidGatewayTest {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Test
    public void testCreateAcid() throws SQLException {
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGate = new tableAcidGateway(1);

            assertEquals(1, acidGate.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testFindBySolute() throws SQLException {
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(2);
            Acid findAcid = tableAcidGateway.findBySolute(2);

            assertNotNull(findAcid);
            assertEquals(2, findAcid.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testFindAll() throws SQLException {
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(5);
            acidGateway.insertRow(7);
            acidGateway.insertRow(8);
            acidGateway.insertRow(10);

            ArrayList<Acid> allAcidRecords = tableAcidGateway.findAll();

            assertNotNull(allAcidRecords);
            assertEquals(11, allAcidRecords.size());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testPersist() throws SQLException {
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(11);
            assertEquals(11, acidGateway.getSolute());
            acidGateway.persist();

            Acid findAcid = tableAcidGateway.findBySolute(11);

            assertNotNull(findAcid);
            assertEquals(11, findAcid.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testDelete() throws SQLException {
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(16);
            Acid findAcid = tableAcidGateway.findBySolute(16);
            assertNotNull(findAcid);
            assertEquals(16, findAcid.getSolute());

            acidGateway.delete();
            findAcid = tableAcidGateway.findBySolute(16);

            assertNull(findAcid);
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}