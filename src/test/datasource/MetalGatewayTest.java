package datasource;

import gatewayDTOs.MetalDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MetalGatewayTest {
    /**
     * test a metal row can be created
     */
    @Test
    public void testCreateMetal(){
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        //create a new row for metal table
        MetalGateway gateway = new MetalGateway("Gold", 79, 196.96);

        assertEquals(1,gateway.getId());
        assertEquals("Gold", gateway.getName());
        assertEquals(79,gateway.getAtomicNum());
        assertEquals(196.96, gateway.getAtomicMass(), 0.0001);
    }

    /**
     * Test to see if the row can be deleted.
     */
    @Test
    public void testDeleteMetal(){
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        //creates a row in the metal table
        MetalGateway gateway = new MetalGateway("Silver",47,107.86);
        //test for deleting the row
        assertEquals(1,gateway.getId());
        assertEquals("Silver", gateway.getName());
        assertEquals(47,gateway.getAtomicNum());
        assertEquals(107.86, gateway.getAtomicMass(), 0.0001);

        assertTrue(gateway.delete());
    }

    /**
     * testing updating row for the metal table
     */
    @Test
    public void testUpdateMetal(){
        //Rest the id key and metal table
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        MetalGateway gateway = new MetalGateway("Copper",29, 63.546);

        assertEquals(1,gateway.getId());
        assertEquals("Copper", gateway.getName());
        assertEquals(29,gateway.getAtomicNum());
        assertEquals(63.546, gateway.getAtomicMass(), 0.0001);

        //set the atomic number to different value
        gateway.setAtomicNum(10);
        assertEquals(10, gateway.getAtomicNum());

        assertTrue(gateway.update());

    }
    @Test
    public void testCorrectID(){
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        MetalGateway Goldgateway = new MetalGateway("Gold", 79, 196.96);
        MetalGateway Silvergateway = new MetalGateway("Silver",47,107.86);
        MetalGateway Coppergateway = new MetalGateway("Copper",29, 63.546);

        //Testing ID incrementation
        assertEquals(1,Goldgateway.getId());
        assertEquals(2,Silvergateway.getId());
        assertEquals(3, Coppergateway.getId());
    }

    @Test
    public void testFindingMetalById(){
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        MetalGateway gateway = new MetalGateway("Cobalt", 27, 58.933);

        MetalDTO CobaltDTO = gateway.findByAtomicNumber(27);

        assertEquals(1, CobaltDTO.getId());
        assertEquals("Cobalt", CobaltDTO.getName());
        assertEquals(27,CobaltDTO.getAtomicNum());
        assertEquals(58.933, gateway.getAtomicMass(), 0.0001);

    }

    @Test
            public void CreateMetals() {
        KeyRowDataGateway.reset();
        MetalGateway.createTable();

        //Creating metal elements
        MetalGateway Goldgateway = new MetalGateway("Gold", 79, 196.96);
        MetalGateway Silvergateway = new MetalGateway("Silver",47,107.86);
        MetalGateway Coppergateway = new MetalGateway("Copper",29, 63.546);
        MetalGateway Colbaltgateway = new MetalGateway("Cobalt", 27, 58.933);
    }

}
