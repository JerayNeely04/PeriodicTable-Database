package datasource;

import gatewayDTOs.AcidDTO;
import java.util.ArrayList;
import java.sql.Connection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AcidGatewayTest {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

//    @Test
//    public void testCreateTable() throws DataException {
//        AcidTableGateway.createTable();
//    }
    @AfterEach
    public void rollback() throws SQLException {
        conn.rollback();
    }

    @BeforeEach
    public void setAutoCommitToFalse() throws SQLException {
        conn.setAutoCommit(false);
    }

    @Test
    public void testCreateAcid() throws SQLException {
        try {
            ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("bob");
            long id = chemicalGateway.getId();

            AcidTableGateway acidGate = new AcidTableGateway(id);


            assertEquals(id, acidGate.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testFindBySolute() throws SQLException {
        try {
            ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("bob");
            long id = chemicalGateway.getId();

            AcidTableGateway acidGateway = new AcidTableGateway(id);
            AcidDTO findAcid = AcidTableGateway.findBySolute(id);

            assertNotNull(findAcid);
            assertEquals(id, findAcid.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testFindAll() throws SQLException {
        try {
            ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("bob");
            long id = chemicalGateway.getId();

            AcidTableGateway acidGateway = new AcidTableGateway(id);
            new AcidTableGateway(id);
            new AcidTableGateway(id);
            new AcidTableGateway(id);

            ArrayList<AcidDTO> allAcidRecords = AcidTableGateway.findAll();

            assertNotNull(allAcidRecords);
            assertEquals(4, allAcidRecords.size());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testPersist() throws SQLException {

        try {
            ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("bob");
            long id = chemicalGateway.getId();
            AcidTableGateway acidGateway = new AcidTableGateway(id);
            assertEquals(id, acidGateway.getSolute());

            ChemicalTableGateway chemicalGateway2 = ChemicalTableGateway.createChemical("steve");
            long newID = chemicalGateway2.getId();

            acidGateway.setSolute(newID);

            acidGateway.persist();

            AcidDTO findAcid = acidGateway.findBySolute(newID);

            assertNotNull(findAcid);
            assertEquals(newID, findAcid.getSolute());
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Test
    public void testDelete() throws SQLException {

        try {
            ChemicalTableGateway chemicalGateway = ChemicalTableGateway.createChemical("bob");
            long id = chemicalGateway.getId();
            AcidTableGateway acidGateway = new AcidTableGateway(id);

            AcidDTO findAcid = AcidTableGateway.findBySolute(id);
            assertNotNull(findAcid);
            assertEquals(id, findAcid.getSolute());

            acidGateway.delete();
            findAcid = AcidTableGateway.findBySolute(id);

            assertNull(findAcid);
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}