package DomainModel.Mapper;

import DomainModel.Base;
import datasource.DatabaseConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class BaseMapperTest {
    private Connection conn = DatabaseConnection.getInstance().getConnection();

    @Test
    public void CreateBaseTest() throws SQLException {
        conn.setAutoCommit(false);
        BaseMapper mapper = new BaseMapper(1, 2);
        Base base = mapper.getMyBase();

        assertEquals(1, base.getId());
        assertEquals(2, base.getSolute());
        conn.rollback();
    }

    @Test
    public void testFindBase() throws SQLException {
        BaseMapper mapper = new BaseMapper(2);
        Base base = mapper.getMyBase();

        assertEquals(1, base.getId());
        assertEquals(2, base.getSolute());
    }
}
