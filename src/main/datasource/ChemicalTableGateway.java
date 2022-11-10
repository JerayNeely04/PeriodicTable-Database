package datasource;

import DomainModel.Mapper.ChemicalNotFoundException;
import DomainModel.Mapper.ElementNotFoundException;
import gatewayDTOs.ChemicalDTO;

import java.sql.*;
import java.util.ArrayList;

public class ChemicalTableGateway {

    private long id;
    private String name;

    private Connection connection = null;

    /**
     * The constructor for the Chemical table Gateway
     * @param id the primary key of the Element table
     * @throws DataException SQL exception if we failed to SELECT FROM the Chemical Table!
     */
    public ChemicalTableGateway(long id) throws ChemicalNotFoundException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ChemicalTable WHERE id = " + id;

        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            name = results.getString("name");
        } catch (SQLException e) {
            throw new ChemicalNotFoundException("Failed to create Chemical gateway!", e);
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
    public static ChemicalTableGateway createChemical(String name) throws DataException, ChemicalNotFoundException {
        String query = "INSERT INTO ChemicalTable (name) VALUES (?)";
        long id = 0;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }
            stmt.close();

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
        String query = "UPDATE ChemicalTable SET name = ? WHERE id = " + id;

        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, name);
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
    public static ChemicalTableGateway findById(long id) throws ChemicalNotFoundException {
        return new ChemicalTableGateway(id);
    }

    /**
     *
     */
    public static ChemicalDTO findByName(String name) throws ChemicalNotFoundException {
        String query = "SELECT * FROM ChemicalTable WHERE name = '" + name + "'";
        try(PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();
            results.next();

            return new ChemicalDTO(results.getLong("id"), results.getString("name"));
        } catch (SQLException e) {
            throw new ChemicalNotFoundException("Failed to find chemical", e);
        }
    }

    /**
     * Delete the current row for the chemical table.
     * @return a boolean if one row is affected after we execute the query.
     * @throws DataException SQL exception if we cannot delete from the table.
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM ChemicalTable WHERE id = " + id;
        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
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
    public static ArrayList<ChemicalDTO> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ChemicalTable ORDER BY id";
        ArrayList<ChemicalDTO> chemicalsList = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                ChemicalDTO chemical = createChemicalRecord(results);
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
    private static ChemicalDTO createChemicalRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");
            return new ChemicalDTO(id, name);
        } catch (SQLException e) {
            throw new DataException("Could not create a Chemical DTO!", e);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
