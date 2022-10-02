package datasource;

import java.sql.*;

public class MetalTableGateway {
    private long id;
    private long dissolvedBy;
    private final Connection connection;
    public MetalTableGateway(long id) throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MetalTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.dissolvedBy = results.getLong("dissolvedBy");
        } catch (SQLException e) {
            System.out.println("Failed to create gateway");
        }
    }
    public static MetalTableGateway createMetal(long dissolvedBy) throws SQLException {
            String query = new String("INSERT INTO MetalTable (dissolvedBy) VALUES (?)");
            Connection conn = DatabaseConnection.getInstance().getConnection();
            long id = 0;
            try {
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, dissolvedBy);
                if (stmt.executeUpdate() > 0) {
                    id = getIDFromDatabase(stmt);
                }
            } catch (SQLException e) {
                // throw exception later
                System.out.println("Create metal table failed");
            }
            return new MetalTableGateway(id);
    }
//    public RecordSet FindByAcid(String name, int atomicNum, double atomicMass, long dissolvedBy) {
//
//    }
    public void updateMetal() {
        String query = "UPDATE MetalTable SET dissolvedBy = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setLong(1, this.dissolvedBy);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Data not persisted to the database.");
        }
    }
    public boolean delete() {
        String query = "DELETE FROM MetalTable WHERE id = " + id;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the table!");
        }
        return false;
    }
    public void persist() {
        String query = "UPDATE MetalTable SET dissolvedBy = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setLong(1, this.dissolvedBy);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Data not persisted to the database.");
        }
    }
    private static long getIDFromDatabase(PreparedStatement stmt) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
