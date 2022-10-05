package datasource;

import org.junit.Test;

import static org.junit.Assert.*;

public class madeOfTableTest {
    @Test
    public void testSetCompoundID() {
        madeOfTable.createTable();
        madeOfTable madeOf = new madeOfTable();
        madeOf.setCompoundID(20);

        assertEquals(20, madeOf.getCompoundID());
    }

    @Test
    public void testSetElementID() {
        madeOfTable.createTable();
        madeOfTable madeOf = new madeOfTable();
        madeOf.setElementID(20);

        assertEquals(20, madeOf.getElementID());
    }
}
