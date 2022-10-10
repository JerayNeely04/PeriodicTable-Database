package datasource;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import gatewayDTOs.madeOf;

import static org.junit.Assert.*;

public class madeOfTableTest {
    @Test
    public void testSetCompoundID() {
        try {
            madeOfTable madeOf = new madeOfTable();
            madeOf.setCompoundID(20);

            assertEquals(20, madeOf.getCompoundID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetElementID() {
        try {
            madeOfTable madeOf = new madeOfTable();
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
            madeOfTable madeOf = new madeOfTable();
            madeOf.insertRow(2, 16);
            madeOf.insertRow(8, 24);
            madeOf.insertRow(11, 25);

            ArrayList<madeOf> madeOfArray = madeOfTable.findAll();

            assertNotNull(madeOfArray);
            assertEquals(7, madeOfArray.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindCompoundID() {
        try {
            madeOf compoundID = madeOfTable.findByCompoundID(4);

            assertNotNull(compoundID);
            assertEquals(4, compoundID.getCompoundID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindElementID() {
        try {
            madeOf compoundID = madeOfTable.findByElementID(25);

            assertNotNull(compoundID);
            assertEquals(25, compoundID.getElementID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertRow() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            madeOfTable madeOf = new madeOfTable();
            madeOf.insertRow(1, 16);

            madeOf elementID = madeOfTable.findByElementID(16);
            madeOf compoundID = madeOfTable.findByCompoundID(1);

            assertNotNull(elementID);
            assertNotNull(compoundID);

            assertEquals(16, elementID.getElementID());
            assertEquals(1, compoundID.getCompoundID());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }
}
