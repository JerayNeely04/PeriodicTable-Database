package datasource;

import gatewayDTOs.Chemical;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChemicalGatewayTest {

    @Test
    public void createChemicalTable() {
        ChemicalTableGateway.createTable();
    }

    @Test
    public void testCreateChemical() throws SQLException {
        Chemical Carbon = new Chemical(1, "Carbon");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Carbon");

        assertEquals(Carbon.getName(), chemicalGateway.chemicalDTO.getName());
    }

    @Test
    public void testPersistChemical() throws SQLException {
        Chemical chem = new Chemical(2, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
        chemicalGateway.chemicalDTO.setName("Lead");
        chem.setName("Lead");
        chemicalGateway.persist();

        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
    }

    @Test
    public void testDeleteChemical() throws SQLException {
        Chemical chem = new Chemical(3, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertTrue(chemicalGateway.delete());
    }
}
