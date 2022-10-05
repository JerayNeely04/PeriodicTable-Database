package datasource;

import gatewayDTOs.Chemical;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChemicalGatewayTest {

    /**
     * Creates the Chemical table in the database.
     * @throws DataException if we cannot create the table.
     */
    @Test
    public void createChemicalTable() throws DataException {
        ChemicalTableGateway.createTable();
    }

    /**
     * testing if we can create a chemical in the Chemical table.
     * @throws DataException if we cannot create the chemical.
     */
    @Test
    public void testCreateChemical() throws DataException {
        Chemical Carbon = new Chemical(1, "Carbon");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Carbon");

        assertEquals(Carbon.getName(), chemicalGateway.chemicalDTO.getName());
    }

    /**
     * Testing the update method.
     * @throws DataException SQL Exception if we cannot update to the database.
     */
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

    /**
     * Testing the delete method.
     * @throws DataException SQL Exception if we cannot delete from the database.
     */
    @Test
    public void testDeleteChemical() throws DataException {
        Chemical chem = new Chemical(3, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertTrue(chemicalGateway.delete());
    }

    /**
     * Testing the find method
     * @throws DataException SQL exception if we cannot find a Chemical.
     */
    @Test
    public void testFindByID() throws DataException {
        ChemicalTableGateway chemicalTableGateway = ChemicalTableGateway.findById(2);
        assertEquals("Lead", chemicalTableGateway.chemicalDTO.getName());
    }

    /**
     * Testing the findAll method that returns all the chemicals.
     * @throws DataException if cannot return all the chemicals.
     */
    @Test
    public void testFindAll() throws DataException {
        ArrayList<Chemical> chemicalsList = ChemicalTableGateway.findAll();
        assertEquals(12, chemicalsList.size());
    }
}
