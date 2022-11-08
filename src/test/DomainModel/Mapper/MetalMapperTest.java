package DomainModel.Mapper;

//import DomainModel.Metal;
//import datasource.DatabaseConnection;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//
//public class MetalMapperTest {
//    private Connection conn = DatabaseConnection.getInstance().getConnection();
//
//    /**
//     * Tests to make sure an element can be created from the mappers constructor
//     * @throws SQLException
//     */
//    @Test
//    public void testCreateMetal() throws SQLException {
//        conn.setAutoCommit(false);
//        MetalMapper mapper = new MetalMapper(1, 2);
//        Metal metal = mapper.getMyMetal();
//
//        assertEquals(1, metal.getId());
//        assertEquals(2, metal.getDissolvedBy(), 0.0001);
//        conn.rollback();
//    }
//
//    /**
//     * Tests to make sure the finder constructor works
//     * @throws SQLException
//     */
//    @Test
//    public void testFindMetal() throws SQLException {
//        MetalMapper mapper = new MetalMapper(2);
//        Metal metal = mapper.getMyMetal();
//
//        assertEquals(1, metal.getId());
//        assertEquals(2, metal.getDissolvedBy(), 0.0001);
//    }
//}
