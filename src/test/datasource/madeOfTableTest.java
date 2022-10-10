package datasource;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import GatewayDTO.madeOfDTO;

import static org.junit.Assert.*;

public class madeOfTableTest {

    @Test
    public void testSetCompoundID() {
        try {
            MadeOfTableGateway madeOf = new MadeOfTableGateway();
            madeOf.setCompoundID(20);

            assertEquals(20, madeOf.getCompoundID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetElementID() {
        try {
            MadeOfTableGateway madeOf = new MadeOfTableGateway();
            madeOf.setElementID(20);

            assertEquals(20, madeOf.getElementID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            SingleTableGateway.createCompound("H20", 2, 16);
            SingleTableGateway.createCompound("02", 8, 24);
            SingleTableGateway.createCompound("NaCl", 11, 25);

            MadeOfTableGateway madeOf = new MadeOfTableGateway();
            madeOf.insertRow(2, 16);
            madeOf.insertRow(8, 24);
            madeOf.insertRow(11, 25);

            ArrayList<madeOfDTO> madeOfArray = MadeOfTableGateway.findAll();

            assertNotNull(madeOfArray);
            assertEquals(3, madeOfArray.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindCompoundID() {
        try {
            madeOfDTO compoundID = MadeOfTableGateway.findByCompoundID(4);

            assertNotNull(compoundID);
            assertEquals(4, compoundID.getCompoundID());
        } catch (AssertionError | DataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindElementID() {
        try {
            madeOfDTO compoundID = MadeOfTableGateway.findByElementID(25);

            assertNotNull(compoundID);
            assertEquals(25, compoundID.getElementID());
        } catch (AssertionError | DataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertRow() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        SingleTableGateway.createCompound("H20", 2, 16);

        MadeOfTableGateway madeOf = new MadeOfTableGateway();
        madeOf.insertRow(2, 16);

        madeOfDTO elementID = MadeOfTableGateway.findByElementID(16);
        madeOfDTO compoundID = MadeOfTableGateway.findByCompoundID(2);

        assertNotNull(elementID);
        assertNotNull(compoundID);

        assertEquals(16, elementID.getElementID());
        assertEquals(2, compoundID.getCompoundID());
    }
}
