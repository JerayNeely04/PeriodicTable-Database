package datasource;

import DomainModel.Mapper.ChemicalNotFoundException;
import gatewayDTOs.ChemicalDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChemicalGatewayTest {

    private Connection conn = DatabaseConnection.getInstance().getConnection();

    /**
     * Testing if we can create a chemical in the Chemical table.
     * @throws SQLException if we cannot create the chemical.
     */
    @Test
    public void testCreateChemical() throws SQLException {
        //conn.setAutoCommit(false);

        ChemicalDTO Carbon = new ChemicalDTO(1, "Carbon");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Carbon");

        assertEquals(Carbon.getName(), chemicalGateway.chemicalDTO.getName());
        //conn.rollback();
    }

//    /**
//     * Testing the update method.
//     * @throws SQLException SQL Exception if we cannot update to the database.
//     */
//    @Test
//    public void testPersistChemical() throws SQLException {
//        conn.setAutoCommit(false);
//
//        ChemicalDTO chem = new ChemicalDTO(2, "Mercury");
//        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
//        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
//        chemicalGateway.chemicalDTO.setName("Lead");
//        chem.setName("Lead");
//        chemicalGateway.persist();
//
//        assertEquals(chem.getName(), chemicalGateway.chemicalDTO.getName());
//    }

    /**
     * Testing the delete method.
     * @throws SQLException SQL Exception if we cannot delete from the database.
     */
    @Test
    public void testDeleteChemical() throws SQLException {
        conn.setAutoCommit(false);
        ChemicalDTO chem = new ChemicalDTO(3, "Mercury");
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Mercury");
        assertTrue(chemicalGateway.delete());
    }

    /**
     * Testing the find method
     * @throws DataException SQL exception if we cannot find a Chemical.
     */
    @Test
    public void testFindByID() throws ChemicalNotFoundException {
        ChemicalTableGateway chemicalTableGateway = ChemicalTableGateway.findById(2);
        assertEquals("Lead", chemicalTableGateway.chemicalDTO.getName());
    }

    /**
     * Testing the findAll method that returns all the chemicals.
     * @throws DataException if we cannot return all the chemicals.
     */
    @Test
    public void testFindAll() throws DataException {
        ArrayList<ChemicalDTO> chemicalsList = ChemicalTableGateway.findAll();
        assertEquals(11, chemicalsList.size());    /* We already have 11 rows in our database by default */
    }
}
