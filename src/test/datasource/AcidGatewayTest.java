package datasource;

import gatewayDTOs.Acid;
import java.util.ArrayList;
import java.sql.Connection;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AcidGatewayTest {
    @Test
    public void testCreateAcid() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGate = new tableAcidGateway(1);

            assertEquals(1, acidGate.getSolute());
        } finally {
            conn.rollback();
            conn.close();
        }
    }

    @Test
    public void testFindBySolute() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(2);
            Acid findAcid = tableAcidGateway.findBySolute(2);

            assertNotNull(findAcid);
            assertEquals(2, findAcid.getSolute());
        } finally {
            conn.rollback();
            conn.close();
        }
    }

    @Test
    public void testFindAll() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(5);
            acidGateway.insertRow(7);
            acidGateway.insertRow(8);
            acidGateway.insertRow(10);

            ArrayList<Acid> allAcidRecords = tableAcidGateway.findAll();

            assertNotNull(allAcidRecords);
            assertEquals(4, allAcidRecords.size());
        } finally {
            conn.rollback();
            conn.close();
        }
    }

    @Test
    public void testPersist() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(11);
            assertEquals(11, acidGateway.getSolute());
            acidGateway.persist();

            Acid findAcid = tableAcidGateway.findBySolute(11);

            assertNotNull(findAcid);
            assertEquals(11, findAcid.getSolute());
        } finally {
            conn.rollback();
            conn.close();
        }
    }

    @Test
    public void testDelete() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            tableAcidGateway acidGateway = new tableAcidGateway(16);
            Acid findAcid = tableAcidGateway.findBySolute(16);
            assertNotNull(findAcid);
            assertEquals(16, findAcid.getSolute());

            acidGateway.delete();
            findAcid = tableAcidGateway.findBySolute(16);

            assertNull(findAcid);
        } finally {
            conn.rollback();
            conn.close();
        }
    }
}