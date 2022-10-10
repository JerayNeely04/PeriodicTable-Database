package datasource;

import gatewayDTOs.Chemical;
import gatewayDTOs.Element;

import java.sql.*;
import java.util.ArrayList;

public class ChemicalTableGateway {

    protected Chemical chemicalDTO;              /* I used this to act as a DTO and hold info for the current Chemical */
    private Connection connection = null;

    /**
     * The constructor for the Chemical table Gateway
     * @param id the primary key of the Element table
     * @throws DataException SQL exception if we failed to SELECT FROM the Chemical Table!
     */
    public ChemicalTableGateway(long id) throws DataException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ChemicalTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            chemicalDTO = new Chemical(id,results.getString("name"));
        } catch (SQLException e) {
            throw new DataException("Failed to create Chemical gateway!", e);
        }

    }

    /**
     * Create the Chemical table in the database, drop if it's already there just for
     * testing purposes!
     * @throws DataException SQL Exception if we cannot create the table!
     */
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String createStatement =
                "CREATE TABLE ChemicalTable (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50))";

        try {
            PreparedStatement stmt;
            // create new table
            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException("Failed to create Chemical Table!", e);
        }
    }

    /**
     * Creat constructor for a chemical in the Chemical table. Uses an auto-generated number for the id.
     * @param name the name of the chemical we want to create.
     * @return the new constructor with the generated id of the created chemical.
     * @throws DataException SQL Exception if we cannot create a Chemical.
     */
    public static ChemicalTableGateway createChemical(String name) throws DataException {
        String query = "INSERT INTO ChemicalTable (name) VALUES (?)";
        long id = 0;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            throw new DataException("Create Chemical failed!", e);
        }
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

    /**
     * Updating the current Chemical in the Chemical table.
     * @throws DataException SQL Exception if we failed to update to the chemical table.
     */
    public void persist() throws DataException {
        String query = "UPDATE ChemicalTable SET name = ? WHERE id = " + chemicalDTO.getId();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, chemicalDTO.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Data not persisted to the chemical table!", e);
        }
    }

    /**
     * find constructor that uses the id to find a Chemical.
     * @param id the primary key for the Chemical table
     * @return the new constructor with the specified id.
     * @throws DataException SQL exception if cannot find the chemical.
     */
    public static ChemicalTableGateway findById(long id) throws DataException {
        return new ChemicalTableGateway(id);
    }

    /**
     * Delete the current row for the chemical table.
     * @return a boolean if one row is affected after we execute the query.
     * @throws DataException SQL exception if we cannot delete from the table.
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM ChemicalTable WHERE id = " + chemicalDTO.getId();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DataException("Failed to delete a row in the chemical table!", e);
        }
    }

    /**
     * Find all the chemicals from the chemical table.
     * @return an array list with all the DTO's of the Chemical table.
     * @throws DataException SQL exception if we cannot find all the chemicals.
     */
    public static ArrayList<Chemical> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ChemicalTable ORDER BY id";
        ArrayList<Chemical> chemicalsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Chemical chemical = createChemicalRecord(results);
                chemicalsList.add(chemical);
            }
            return chemicalsList;
        } catch (SQLException e) {
            throw new DataException("Could not fetch all chemicals for the Class Table!", e);
        }
    }

    /**
     * Creating a chemical object to hold the information about the DTO's for the findAll().
     * @param results the result set from executing the query in the findAll().
     * @return new Chemical object.
     * @throws DataException SQL exception if we cannot create the Chemical record.
     */
    private static Chemical createChemicalRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");
            return new Chemical(id, name);
        } catch (SQLException e) {
            throw new DataException("Could not create a Chemical DTO!", e);
        }
    }
}
