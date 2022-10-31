package datasource;

import DomainModel.Base;
import gatewayDTOs.BaseDTO;
import gatewayDTOs.ChemicalDTO;

import java.sql.*;
import java.util.ArrayList;

public class BaseTableGateway {
    protected BaseDTO baseDTO;
    private Connection connection = null;

    /**
     * The Constructor for Base Table Gateway
     * @param id
     */
    public BaseTableGateway(long id) throws DataException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable WHERE id = " + id;

        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();

            baseDTO = new BaseDTO(id, rs.getLong(""));
        } catch (SQLException e) {
            throw new DataException("Failed to create Base gateway!", e);
        }
    }

    /**
     * Creates the Base Table in the database
     */
    public static void createTable() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String createStatement = "CREATE TABLE BaseTable (" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "solute BIGINT, " +
                "FOREIGN KEY (solute) REFERENCES ChemicalTable(id) ON DELETE CASCADE" +
                ")";

        try {
            PreparedStatement stmt;

            // create new table
            stmt = connection.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Create constructor for a base in the Base Table
     * @param solute containing the info for the DTO
     * @return the new DTO
     */
    public static BaseTableGateway createBase(long solute) throws DataException {
        String query = "INSERT INTO BaseTable (solute) VALUES (?)";
        long id = 0;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, solute);
            if(stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return new BaseTableGateway(id);
    }

    /**
     * Get the generated id from the database.
     * @param stmt is the prepared statement from the creat constructor.
     * @return the generated id.
     * @throws DataException SQL exception if we cannot get the ID from the base table.
     */
    private static long getIDFromDatabase(PreparedStatement stmt) throws DataException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataException("Cannot get Base ID from Base Table!", e);
        }
        return 0;
    }


    /**
     * Find all Base from the Base Table
     * @return an array list with all the DTO's of the Base Table
     */
    public static ArrayList<BaseDTO> findAll() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable ORDER BY id";
        ArrayList<BaseDTO> baseList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                BaseDTO base = createBaseRecord(rs);
                baseList.add(base);
            }
            return baseList;
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Creating a base object to hold the information about the DTO's for the findAll().
     * @param results the result set from executing the query in the findAll().
     * @return new Base object.
     * @throws DataException SQL exception if we cannot create the Base record.
     */
    private static BaseDTO createBaseRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            long solute = results.getLong("solute");
            return new BaseDTO(id, solute);
        } catch (SQLException e) {
            throw new DataException("Could not create a Base DTO!", e);
        }
    }

    /**
     * find constructor that uses the solute to find the Base
     * @param solute
     * @return the new constructor with the specified solute
     * @throws DataException
     */
    public static BaseDTO findSolute(long solute) throws DataException {
        String query = "SELECT * FROM BaseTable WHERE solute = '" + solute + "'";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return new BaseDTO(rs.getLong("id"), rs.getLong("solute"));
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Delete the current row for the Base table
     * @return a boolean if one row is affected after we execute the query
     * @throws DataException
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM BaseTable WHERE id = " + baseDTO.getId();
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
    /**
     * Updating the current Base in the Base Table
     */
    public void persist() throws DataException {
        String query = "UPDATE BaseTable SET solute = ? WHERE id =" + baseDTO.getId();

        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setLong(1, baseDTO.getSolute());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}