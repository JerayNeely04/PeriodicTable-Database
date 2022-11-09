package datasource;

import gatewayDTOs.MetalDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MetalGatewayTest {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @AfterEach
    public void rollback() throws SQLException {
        conn.rollback();
    }

    @BeforeEach
    public void setAutoCommitToFalse() throws SQLException {
        conn.setAutoCommit(false);
    }

//    @Test
//    public void testCreateTable() throws DataException {
//        MetalTableGateway.createTable();
//    }

    /**
     * Testing if we can create Metal in the Metal Table
     * @throws SQLException if we cannot create Table
     */
    @Test
    public void testCreateMetal() throws SQLException
    {
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Oxygen");
        long id = chemicalGateway.getId();
        new AcidTableGateway(id);
        MetalTableGateway metalGateway = new MetalTableGateway(id);

        assertEquals(id, metalGateway.getDissolvedBy());
    }

    /**
     * Testing the find method
     * @throws SQLException
     */
    @Test
    public void testFindDissolvedBy() throws SQLException {
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Oxygen");
        long id = chemicalGateway.getId();
        new AcidTableGateway(id);
        new MetalTableGateway(id);

        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(id);

        assertNotNull(findMetal);
        assertEquals(id, findMetal.getDissolvedBy());
    }

    /**
     * Testing the find all method
     * @throws SQLException if we cannot return all the Metal
     */
    @Test
    public void testFindAllMetal() throws SQLException {
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Oxygen");
        long id = chemicalGateway.getId();
        new AcidTableGateway(id);
        new MetalTableGateway(id);

        new AcidTableGateway(id);
        new MetalTableGateway(id);

        new AcidTableGateway(id);
        new MetalTableGateway(id);

        new AcidTableGateway(id);
        new MetalTableGateway(id);

        ArrayList<MetalDTO> allMetalRecords = MetalTableGateway.findAll();

        assertNotNull(allMetalRecords);
        assertEquals(4, allMetalRecords.size());
    }

    /**
     * Testing the update method
     * @throws SQLException if we cannot update the database
     */
    @Test
    public void testPersistMetal() throws SQLException {
        //TODO: Not changing anything with the persist
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Oxygen");
        long id = chemicalGateway.getId();
        new AcidTableGateway(id);
        MetalTableGateway metalGateway = new MetalTableGateway(id);

        assertEquals(id, metalGateway.getDissolvedBy());

        metalGateway.persist();

        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(id);

        assertNotNull(findMetal);
        assertEquals(id, findMetal.getDissolvedBy());
    }

    /**
     * Testing the delete method
     * @throws SQLException if we cannot delete from the database
     */
    @Test
    public void testDeleteMetal() throws SQLException {
        ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("Oxygen");
        long id = chemicalGateway.getId();
        new AcidTableGateway(id);
        MetalTableGateway metalGateway = new MetalTableGateway(id);

        MetalDTO findMetal = MetalTableGateway.findDissolvedBy(id);
        assertNotNull(findMetal);
        assertEquals(id, findMetal.getDissolvedBy());

        metalGateway.delete();
        findMetal = MetalTableGateway.findDissolvedBy(id);

        assertNull(findMetal);
    }
}