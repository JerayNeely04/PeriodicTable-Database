package datasource;

import gatewayDTOs.Chemical;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChemicalGatewayTest {

    @Test
    public void createChemicalTable() throws DataException {
        ChemicalTableGateway.createTable();
    }

    @Test
    public void testCreateChemical() throws DataException {
        Chemical Carbon = new Chemical(1, "Carbon");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Carbon");

        assertEquals(Carbon.getName(), chemicalGateway.chemicalDTO.getName());
    }

    @Test
    public void testPersistChemical() throws DataException {
        Chemical chem = new Chemical(2, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
        chemicalGateway.chemicalDTO.setName("Lead");
        chem.setName("Lead");
        chemicalGateway.persist();

        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
    }

    @Test
    public void testDeleteChemical() throws DataException {
        Chemical chem = new Chemical(3, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertTrue(chemicalGateway.delete());
    }

    @Test
    public void testFindByID() throws DataException {
        ChemicalTableGateway chemicalTableGateway = ChemicalTableGateway.findById(2);
        assertEquals("Lead", chemicalTableGateway.chemicalDTO.getName());
    }

    @Test
    public void testFindAll() throws DataException {
        ArrayList<Chemical> chemicalsList = ChemicalTableGateway.findAll();
        assertEquals(12, chemicalsList.size());
    }
}
