package datasource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeyRDGTest {

    /**
     * Creates the table without a runner
     */
    @Test
    public void createTable()
    {
        KeyRDG.createTable();
    }

    /**
     * Tests to make sure generateId returns the next usable ID to use as a primary key
     */
    @Test
    public void testKeyIncrement()
    {
        assertEquals(1, KeyRDG.generateId());
        assertEquals(2, KeyRDG.generateId());
        assertEquals(3, KeyRDG.generateId());
    }

    /**
     * Tests to make sure the key table can be reset back to 0
     */
    @Test
    public void testKeyReset()
    {
        assertEquals(1, KeyRDG.generateId());
        assertEquals(2, KeyRDG.generateId());
        assertEquals(3, KeyRDG.generateId());

        assertTrue(KeyRDG.reset());
        assertEquals(1, KeyRDG.generateId());
    }
}
