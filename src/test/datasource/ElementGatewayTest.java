//package datasource;
//
//import gatewayDTOs.ElementDTO;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class ElementGatewayTest {
//
//    private Connection conn = DatabaseConnection.getInstance().getConnection();
//
//    /**
//     * Testing if we can create an Element in the Element table.
//     * @throws SQLException if we cannot create the Element.
//     */
//    @Test
//    public void testCreateElement() throws SQLException {
//        //conn.setAutoCommit(false);
//
//        ElementDTO Carbon = new ElementDTO(11, 6.2);
//        ElementTableGateway elementGateway = ElementTableGateway.createElement(11, 6.2, 1);
//
//        assertEquals(Carbon.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
//        //conn.rollback();
//    }
//
//    /**
//     * Testing the update method.
//     * @throws SQLException SQL Exception if we cannot update to the database.
//     */
//    @Test
//    public void testPersistElement() throws SQLException {
//        conn.setAutoCommit(false);
//
//        ElementDTO element = new ElementDTO(4, 3.3);
//        ElementTableGateway elementGateway = ElementTableGateway.createElement(4, 3.3, 2);
//        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
//
//        elementGateway.elementDTO.setAtomicMass(8.6);
//        element.setAtomicMass(8.6);
//        elementGateway.persist();
//
//        assertEquals(element.getAtomicMass(), elementGateway.elementDTO.getAtomicMass(), 0.001);
//    }
//
//    /**
//     * Testing the delete method.
//     * @throws DataException SQL Exception if we cannot delete from the database.
//     */
//    @Test
//    public void testDeleteElement() throws DataException {
//        ElementDTO element = new ElementDTO(3, 1.8);
//        ElementTableGateway elementGateway = ElementTableGateway.createElement(3, 1.8, 3);
//        assertTrue(elementGateway.delete());
//    }
//
//    /**
//     * Testing the find method
//     * @throws DataException SQL exception if we cannot find an Element.
//     */
//    @Test
//    public void testFindByAtomicNum() throws DataException {
//        ElementTableGateway elementTableGateway = ElementTableGateway.findByAtomicNumber(24);
//        assertEquals(4.11, elementTableGateway.elementDTO.getAtomicMass(), 0.001);
//    }
//
//    /**
//     * Testing the findAll method that returns all the elements.
//     * @throws DataException if we cannot return all the elements.
//     */
//    @Test
//    public void testFindAll() throws DataException {
//        ArrayList<ElementDTO> elements = ElementTableGateway.findAll();
//        assertEquals(4, elements.size());           /* we have 4 rows in our database table by default */
//    }
//}
