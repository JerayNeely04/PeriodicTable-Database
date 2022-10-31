package datasource;

import java.sql.*;
import java.util.ArrayList;

import DomainModel.Metal;
import gatewayDTOs.ChemicalDTO;
import gatewayDTOs.MetalDTO;

public class MetalTableGateway {
    protected MetalDTO metalDTO;
    private Connection connection = null;

    /**
     * The constructor for the Metal Table Gateway
     * @param id
     */
    public MetalTableGateway(long id) throws DataException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MetalTable WHERE id = " + id;

        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();
            results.next();

            metalDTO = new MetalDTO(id, results.getLong("dissolvedBy"));
        } catch (SQLException e) {
            throw new DataException("Failed to create Metal gateway!", e);
        }
    }

    /**
     * Creates the Metal table in the database
     */
    public static void createTable() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String createStatement = "CREATE TABLE MetalTable (" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
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
     * Create the Metal DTO with information from ResultSet
     * @param dissolvedBy containing the info for the DTO
     * @return the new DTO
     */
    public static MetalTableGateway createMetal(long dissolvedBy) throws DataException {
        String query = "INSERT INTO MetalTable (dissolvedBy) VALUES (?)";
        long id = 0;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, dissolvedBy);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return new MetalTableGateway(id);
    }

    /**
     * Find all metal from the Metal Table
     * @return an array list with all the DTO's of the Metal Table
     */
    public static ArrayList<MetalDTO> findAll() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MetalTable ORDER BY id";
        ArrayList<MetalDTO> metalList = new ArrayList<>();

        try(PreparedStatement stmt = connection.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                MetalDTO metal = createMetalRecord(rs);
                metalList.add(metal);
            }

            return metalList;
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    private static MetalDTO createMetalRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            long dissolvedBy = results.getLong("dissolvedBy");
            return new MetalDTO(id, dissolvedBy);
        } catch (SQLException e) {
            throw new DataException("Could not create a Chemical DTO!", e);
        }
    }

    /**
     * find constructor that uses the dissolvedBy to find the Metal
     * @param dissolvedBy
     * @return the new constructor with the specified dissolvedBy
     * @throws DataException
     */
    public static MetalDTO findDissolvedBy(long dissolvedBy) throws DataException {
        String query = "SELECT * FROM MetalTable WHERE dissolvedBy = '" + dissolvedBy + "'";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return new MetalDTO(rs.getLong("id"), rs.getLong("dissolvedBy"));
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Delete the current row for the Metal table
     * @return a boolean if one row is affected after we execute the query
     * @throws DataException
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM MetalTable WHERE id = " + metalDTO.getId();
        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Updating the current Metal in the Metal Table
     * @return true if changes were made
     */
    public void persist() throws DataException {
        String query = "UPDATE MetalTable SET dissolvedBy = ? WHERE id = " + metalDTO.getId();
        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setLong(1, metalDTO.getDissolvedBy());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    public static ChemicalTableGateway findById(long id) throws DataException {
        return new ChemicalTableGateway(id);
    }

    /**
     * Get the generated id from the database.
     * @param stmt is the prepared statement from the creat constructor.
     * @return the generated id.
     * @throws DataException SQL exception if we cannot get the ID from the chemical table.
     */
    private static long getIDFromDatabase(PreparedStatement stmt) throws DataException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataException("Cannot get Chemical ID from Chemical Table!", e);
        }
        return 0;
    }
}
