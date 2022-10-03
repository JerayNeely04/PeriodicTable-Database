package datasource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeyRowDataGatewayTest {

    /**
     * Creates the table without a runner
     */
    @Test
    public void createTable()
    {
        KeyRowDataGateway.createTable();
    }

    /**
     * Tests to make sure generateId returns the next usable ID to use as a primary key
     */
    @Test
    public void testKeyIncrement()
    {
        assertEquals(1, KeyRowDataGateway.generateId());
        assertEquals(2, KeyRowDataGateway.generateId());
        assertEquals(3, KeyRowDataGateway.generateId());
    }

    /**
     * Tests to make sure the key table can be reset back to 0
     */
    @Test
    public void testKeyReset()
    {
        assertEquals(1, KeyRowDataGateway.generateId());
        assertEquals(2, KeyRowDataGateway.generateId());
        assertEquals(3, KeyRowDataGateway.generateId());

        assertTrue(KeyRowDataGateway.reset());
        assertEquals(1, KeyRowDataGateway.generateId());
    }
}
