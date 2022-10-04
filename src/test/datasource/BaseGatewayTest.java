package datasource;

import gatewayDTOs.Base;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BaseGatewayTest {

    @Test
    public void createBaseTable() { BaseTableGateway.createTable(); }

    @Test
    public void testCreateBase() throws SQLException
    {
        MetalTableGateway metalGateway = new MetalTableGateway(1);

        assertEquals(1, metalGateway.getDissolvedBy());
    }

    @Test
    public void testFindSolute() throws SQLException
    {
        BaseTableGateway baseGateway = new BaseTableGateway(1);
        Base findBase = BaseTableGateway.findSolute(1);

        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());
    }

    @Test
    public void testFindAllBase() throws SQLException {
        // Recreate table
        BaseTableGateway.createTable();

        BaseTableGateway baseGateway = new BaseTableGateway(1);
        baseGateway.insertRow(2);
        baseGateway.insertRow(3);
        baseGateway.insertRow(4);

        ArrayList<Base> allBaseRecords = BaseTableGateway.findAll();

        assertNotNull(allBaseRecords);
        assertEquals(4, allBaseRecords.size());
    }

    @Test
    public void testPersistBase() throws SQLException {
        BaseTableGateway baseGateway = new BaseTableGateway(1);
        assertEquals(1, baseGateway.getSolute());
        baseGateway.persist();

        Base findBase = BaseTableGateway.findSolute(1);

        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());
    }

    @Test
    public void testDeleteBase() throws SQLException {
        BaseTableGateway baseGateway = new BaseTableGateway(1);
        Base findBase = BaseTableGateway.findSolute(1);
        assertNotNull(findBase);
        assertEquals(1, findBase.getSolute());

        baseGateway.delete();
        findBase = BaseTableGateway.findSolute(1);

        assertNull(findBase);
    }
}