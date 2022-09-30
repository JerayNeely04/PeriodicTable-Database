package datasource;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SingleTableGatewayTest {
    @Test
    public void testCreateTable()
    {
        SingleTableGateway.createTable();
    }

    @Test
    public void testCreateChemical() throws SQLException
    {
        SingleTableGateway gateway = SingleTableGateway.createChemical("Boron");
        assertEquals("Boron", gateway.getName());
    }

    @Test
    public void testCreateAcid() throws SQLException
    {
        SingleTableGateway gateway = SingleTableGateway.createAcid("Carbonic Acid", 1, 1);
        assertEquals("Carbonic Acid", gateway.getName());
        assertEquals(1, gateway.getSolute());
        assertEquals(1, gateway.getDissolves());
    }
}