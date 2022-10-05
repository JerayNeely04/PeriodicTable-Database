package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.Metal;

public class MetalTableGateway {
    private long dissolvedBy;
    private final Connection connection;
    public MetalTableGateway(long dissolvedBy)
    {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.dissolvedBy = dissolvedBy;
        this.insertRow(dissolvedBy);
    }
    public static void createTable() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS MetalTable";
        String createStatement = "CREATE TABLE MetalTable (" +
                "dissolvedBy BIGINT, " +
                "FOREIGN KEY (dissolvedBy) REFERENCES acid(id) ON DELETE CASCADE" +
                ")";
        try {
            // drop old table
            PreparedStatement stmt;
            stmt = connection.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            // create new table
            stmt = connection.prepareStatement(createStatement);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("ERROR: cannot create Metal Table");
        }
    }
    public long getDissolvedBy() { return dissolvedBy; }
    public static Metal createMetal(ResultSet rs) {
            try {
                long dissolvedBy = rs.getLong("dissolvedBy");
                return new Metal(dissolvedBy);
            } catch (SQLException e) {
                // throw exception later
                System.out.println("Create metal failed");
            }
            return null;
    }

    public static ArrayList<Metal> findAll() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MetalTable";
        ArrayList<Metal> metalList = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Metal metal = createMentalRecord(rs);
                metalList.add(metal);
            }
            return metalList;
        } catch (SQLException e) {
            System.out.println("Could not fetch all metal");
        }
        return null;
    }

    public static Metal findDissolvedBy(long dissolvedBy) {
        String query = "SELECT * FROM MetalTable WHERE dissolvedBy = " + dissolvedBy;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createMentalRecord(rs);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Metal createMentalRecord(ResultSet results) {
        try {
            long dissolvedBy = results.getLong("dissolvedBy");

            return new Metal(dissolvedBy);
        } catch(SQLException e) {
            System.out.println("Metal was not created");
        }
        return null;
    }

    public boolean delete() {
        String query = "DELETE FROM MetalTable WHERE dissolvedBy = " + dissolvedBy;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the table!");
        }
        return false;
    }
    public boolean persist() {
        String query = "UPDATE MetalTable SET dissolvedBy = ? WHERE dissolvedBy =" + dissolvedBy;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setLong(1, this.dissolvedBy);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Data not persisted to the database.");
        }
        return false;
    }
    public void insertRow(long dissolvedBy) {
        String query = "INSERT INTO MetalTable (dissolvedBy) VALUES (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, dissolvedBy);

            int n = stmt.executeUpdate();
            if(n > 0) {
                System.out.println("Insert SUCCEEDED with " + n + " affected rows");
            } else {
                System.out.println("Insert FAILED");
            }
        } catch (SQLException e) {
            System.out.println("Error: Couldn't insert metal into table");
        }
    }
}
