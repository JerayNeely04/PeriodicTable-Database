package datasource;

import gatewayDTOs.AcidDTO;
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
            AcidTableGateway acidGate = new AcidTableGateway(724, 1);

            assertEquals(1, acidGate.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testFindBySolute() throws SQLException {
        conn.setAutoCommit(false);

        try {
            AcidTableGateway acidGateway = new AcidTableGateway(725, 2);
            AcidDTO findAcid = AcidTableGateway.findBySolute(2);

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
            AcidTableGateway acidGateway = new AcidTableGateway(726, 5);
            acidGateway.insertRow(728, 7);
            acidGateway.insertRow(729, 8);
            acidGateway.insertRow(730, 10);

            ArrayList<AcidDTO> allAcidRecords = AcidTableGateway.findAll();

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
            AcidTableGateway acidGateway = new AcidTableGateway(732, 11);
            assertEquals(11, acidGateway.getSolute());
            acidGateway.persist();

            AcidDTO findAcid = AcidTableGateway.findBySolute(11);

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
            AcidTableGateway acidGateway = new AcidTableGateway(733, 16);
            AcidDTO findAcid = AcidTableGateway.findBySolute(16);
            assertNotNull(findAcid);
            assertEquals(16, findAcid.getSolute());

            acidGateway.delete();
            findAcid = AcidTableGateway.findBySolute(16);

            assertNull(findAcid);
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}