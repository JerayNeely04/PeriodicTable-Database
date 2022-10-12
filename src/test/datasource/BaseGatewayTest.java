package datasource;

import gatewayDTOs.Base;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BaseGatewayTest {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    /**
     * Testing if we can create Base in the Base Table
     * @throws SQLException if we cannot create Table
     */
    @Test
    public void testCreateBase() throws SQLException
    {
        conn.setAutoCommit(false);
        BaseTableGateway baseGateway = new BaseTableGateway(1);

        assertEquals(1, baseGateway.getSolute());
        conn.rollback();
    }

    /**
     * Testing the find method
     * @throws SQLException if we cannot find the solute
     */
    @Test
    public void testFindSolute() throws SQLException
    {
        conn.setAutoCommit(false);
        BaseTableGateway baseGateway = new BaseTableGateway(1);
        Base findBase = BaseTableGateway.findSolute(1);

        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());
        conn.rollback();
    }
    /**
     * Testing the find all method
     * @throws SQLException if we cannot return all the Base
     */
    @Test
    public void testFindAllBase() throws SQLException {
        conn.setAutoCommit(false);

        BaseTableGateway baseGateway = new BaseTableGateway(1);
        baseGateway.insertRow(2);
        baseGateway.insertRow(3);
        baseGateway.insertRow(4);

        ArrayList<Base> allBaseRecords = BaseTableGateway.findAll();

        assertNotNull(allBaseRecords);
        assertEquals(4, allBaseRecords.size());
        conn.rollback();
    }

    /**
     * Testing the update method
     * @throws SQLException if we cannot update the database
     */
    @Test
    public void testPersistBase() throws SQLException {
        conn.setAutoCommit(false);

        BaseTableGateway baseGateway = new BaseTableGateway(1);
        assertEquals(1, baseGateway.getSolute());
        baseGateway.persist();

        Base findBase = BaseTableGateway.findSolute(1);

        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());
        conn.rollback();
    }

    /**
     * Testing the delete method
     * @throws SQLException if we cannot delete from the database
     */
    @Test
    public void testDeleteBase() throws SQLException {
        conn.setAutoCommit(false);

        BaseTableGateway baseGateway = new BaseTableGateway(1);
        Base findBase = BaseTableGateway.findSolute(1);
        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());

        baseGateway.delete();
        findBase = BaseTableGateway.findSolute(1);

        assertNull(findBase);
    }
}