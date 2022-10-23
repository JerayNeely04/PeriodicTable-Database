package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.MetalDTO;

public class MetalTableGateway {
    private long dissolvedBy;
    private final Connection conn;

    /**
     * The constructor for the Metal Table Gateway
     * @param dissolvedBy
     */
    public MetalTableGateway(long dissolvedBy) throws DataException {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.dissolvedBy = dissolvedBy;
        this.insertRow(dissolvedBy);
    }

    /**
     * Creates the Metal table in the database
     */
    public static void createTable() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String createStatement = "CREATE TABLE MetalTable (" +
                "dissolvedBy BIGINT, " +
                "FOREIGN KEY (dissolvedBy) REFERENCES AcidTable(solute) ON DELETE CASCADE" +
                ")";
        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * getter for DissolvedBy
     * @return dissolvedBy
     */
    public long getDissolvedBy() { return dissolvedBy; }

    /**
     * Create the Metal DTO with information from ResultSet
     * @param rs containing the info for the DTO
     * @return the new DTO
     */
    public static MetalDTO createMetal(ResultSet rs) throws DataException {
            try {
                long dissolvedBy = rs.getLong("dissolvedBy");

                return new MetalDTO(dissolvedBy);
            } catch (SQLException e) {
                throw new DataException(e.getMessage());
            }
    }

    /**
     * Find all metal from the Metal Table
     * @return an array list with all the DTO's of the Metal Table
     */
    public static ArrayList<MetalDTO> findAll() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MetalTable";
        ArrayList<MetalDTO> metalList = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                MetalDTO metal = createMetal(rs);
                metalList.add(metal);
            }

            return metalList;
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * find constructor that uses the dissolvedBy to find the Metal
     * @param dissolvedBy
     * @return the new constructor with the specified dissolvedBy
     * @throws DataException
     */
    public static MetalDTO findDissolvedBy(long dissolvedBy) throws DataException {
        String query = "SELECT * FROM MetalTable WHERE dissolvedBy = " + dissolvedBy;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createMetal(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return null;
    }

    /**
     * Delete the current row for the Metal table
     * @return a boolean if one row is affected after we execute the query
     * @throws DataException
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM MetalTable WHERE dissolvedBy = " + dissolvedBy;
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return false;
    }

    /**
     * Updating the current Metal in the Metal Table
     * @return true if changes were made
     */
    public boolean persist() throws DataException {
        String query = "UPDATE MetalTable SET dissolvedBy = ? WHERE dissolvedBy =" + dissolvedBy;
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setLong(1, this.dissolvedBy);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return false;
    }

    /**
     * insert row into Metal Table
     * @param dissolvedBy value insert into Metal Table
     */
    public void insertRow(long dissolvedBy) throws DataException {
        String query = "INSERT INTO MetalTable (dissolvedBy) VALUES (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, dissolvedBy);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}
