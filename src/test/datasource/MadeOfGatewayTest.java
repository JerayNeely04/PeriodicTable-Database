package datasource;

import gatewayDTOs.MadeOfDTO;
import model.mapper.CompoundNotFoundException;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MadeOfGatewayTest {
    Connection conn = DatabaseConnection.getInstance().getConnection();

    /**
     * tests the creation of the MadeOf RDG
     * @throws DataException
     */
    @Test
    public void testCreateMadeOfRow() throws DataException, CompoundNotFoundException {
        new ElementGateway("Nitrogen", 7, 14.0067);
        new CompoundGateway("Carbon Dioxide");

        MadeOfGateway madeOf = new MadeOfGateway(2, 1);
        assertEquals(2, madeOf.getCompoundID());
        assertEquals(1, madeOf.getElementID());

        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DataException("Database couldn't rollback", e);
        }
    }

    /**
     * tests to make sure every row in the made of table is returned
     * @throws SQLException
     */
    @Test
    public void testFindAll() throws SQLException {
        new ElementGateway("Oxygen", 8, 15.999);
        new ElementGateway("Nitrogen", 7, 14.0067);

        new CompoundGateway("Carbon Dioxide");
        new CompoundGateway("Salt");

        new MadeOfGateway(3, 1);
        new MadeOfGateway(4, 2);

        ArrayList<MadeOfDTO> madeOfList = MadeOfGateway.findAll();

        assert madeOfList != null;
        assertEquals(2, madeOfList.size());

        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DataException("Database couldn't rollback", e);
        }
    }


    /**
     * tests to make sure a row of the MadeOf table can be returned by compound id
     * @throws DataException
     */
//    @Test
//    public void testFindCompoundID() throws DataException, CompoundNotFoundException {
//        new ElementGateway("Nitrogen", 7, 14.0067);
//        new CompoundGateway("Carbon Dioxide");
//
//        new MadeOfGateway(2, 1);
//        MadeOfDTO madeOfDTO = MadeOfGateway.findByCompoundID(2);
//
//        assertNotNull(madeOfDTO);
//        assertEquals(2, madeOfDTO.getCompoundID());
//
//        try {
//            conn.rollback();
//        } catch (SQLException e) {
//            throw new DataException("Database couldn't rollback", e);
//        }
//    }

    /**
     * tests to make sure a row of the MadeOf table can be returned by element id
     * @throws DataException
     */
//    @Test
//    public void testFindElementID() throws DataException, CompoundNotFoundException {
//        new ElementGateway("Nitrogen", 7, 14.0067);
//        new CompoundGateway("Carbon Dioxide");
//
//        new MadeOfGateway(2, 1);
//
//        MadeOfDTO madeOfDTO = MadeOfGateway.findByElementID(1);
//
//        assertNotNull(madeOfDTO);
//        assertEquals(1, madeOfDTO.getElementID());
//
//        try {
//            conn.rollback();
//        } catch (SQLException e) {
//            throw new DataException("Database couldn't rollback", e);
//        }
//  }
}
