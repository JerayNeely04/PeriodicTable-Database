package datasource;

import DomainModel.Mapper.ChemicalNotFoundException;
import DomainModel.Mapper.ElementNotFoundException;
import gatewayDTOs.ElementDTO;

import java.sql.*;
import java.util.ArrayList;

public class ElementTableGateway {
    private long id;
    private long atomicNumber;
    private double atomicMass;
    private Connection connection = null;

    /**
     * The constructor for the Element table Gateway
     * @param id the primary key of the Element table
     * @throws DataException SQL exception if we failed to SELECT FROM the Element Table!
     */
    public ElementTableGateway(long id) throws ElementNotFoundException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE id = " + id;

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            atomicNumber = results.getLong("atomicNumber");
            atomicMass = results.getDouble("atomicMass");
        } catch (SQLException e) {
            throw new ElementNotFoundException("Failed to Select Element gateway!", e);
        }
    }

    /**
     * Create the Element table in the database, drop if it's already there just for
     * testing purposes!
     * @throws DataException SQL Exception if we cannot create the table!
     */
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String createStatement =
                "CREATE TABLE ElementTable (atomicNumber BIGINT NOT NULL PRIMARY KEY, atomicMass DOUBLE NOT NULL)";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException("Cannot create Element Table!", e);
        }
    }

    /**
     * Creat constructor for an element in the Element table.
     * @param atomicNumber the primary key for the Element table
     * @param atomicMass the second column for our table.
     * @return the new constructor with the atomic number of the created element.
     * @throws DataException SQL Exception if we cannot create an Element.
     */
    public static ElementTableGateway createElement(long atomicNumber, double atomicMass) throws DataException, ElementNotFoundException {
        String query = "INSERT INTO ElementTable VALUES (?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, atomicNumber);
            stmt.setDouble(2, atomicMass);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            throw new DataException("Cannot crate an Element in the Element Table!", e);
        }
        return new ElementTableGateway(id);
    }

    /**
     * Updating the current Element in the Element table.
     * @throws DataException SQL Exception if we failed to update to the element table.
     */
    public void persist() throws DataException {
        String query = "UPDATE ElementTable SET atomicMass = ?, atomicNumber = ? WHERE id = " + id;

        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setDouble(1, atomicMass);
            stmt.setLong(2, atomicNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Data not persisted to the Element table!", e);
        }
    }

    /**
     * Delete the current row for the element table.
     * @return a boolean if one row is affected after we execute the query.
     * @throws DataException SQL exception if we cannot delete from the table
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM ElementTable WHERE id = " + id;
        try(PreparedStatement stmt = this.connection.prepareStatement(query)) {
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DataException("Failed to delete a row in the Element table!", e);
        }
    }

    /**
     * find constructor that uses the atomicNumber to find an Element.
     * @param atomicNum the primary key for the Element table
     * @return the new constructor with the specified atomicNumber
     * @throws DataException SQL exception if cannot find the element
     */
    public static ElementDTO findByAtomicNumber(long atomicNum) throws DataException, ElementNotFoundException {
        String query = "SELECT * FROM ElementTable WHERE atomicNumber = " + atomicNum;
        try(PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();
            results.next();

            return new ElementDTO(results.getLong("id"), atomicNum, results.getDouble("atomicMass"));
        } catch (ChemicalNotFoundException e) {
            throw new ElementNotFoundException("Failed to find element", e);
        } catch (SQLException e) {
            throw new DataException("SQL failed for find element", e);
        }
    }

    /**
     * Find all the element from the element table.
     * @return an array list with all the DTO's of the Element table.
     * @throws DataException SQL exception if we cannot find all the elements.
     */
    public static ArrayList<ElementDTO> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable ORDER BY id";
        ArrayList<ElementDTO> elementsList = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                ElementDTO element = createElementRecord(results);
                elementsList.add(element);
            }
            return elementsList;
        } catch (SQLException e)
        {
            throw new DataException("Could not fetch all elements for the Class Table!", e);
        }
    }

    public static ArrayList<ElementDTO> findAllBetween(int startAtomicNum, int endAtomicNum) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNumber BETWEEN " + startAtomicNum + " AND " + endAtomicNum;
        ArrayList<ElementDTO> elementsList = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                ElementDTO element = createElementRecord(results);
                elementsList.add(element);
            }
            return elementsList;
        } catch (SQLException e)
        {
            throw new DataException("Could not fetch all elements for the Class Table!", e);
        }
    }

    /**
     * Creating an element object to hold the information about the DTO's for the findAll().
     * @param results the result set from executing the query in the findAll().
     * @return new Element object.
     * @throws DataException SQL exception if we cannot create the Element record.
     */
    private static ElementDTO createElementRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            long atomicNum = results.getLong("atomicNumber");
            double atomicMass = results.getDouble("atomicMass");

            return new ElementDTO(id, atomicNum, atomicMass);
        } catch (SQLException e)
        {
            throw new DataException("Could not create element DTO!", e);
        }
    }

    public static ElementDTO findById(long id) throws ElementNotFoundException {
        String query = "SELECT * FROM ElementTable WHERE id = " + id;
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();
            results.next();

            return new ElementDTO(id, results.getLong("atomicNumber"), results.getDouble("atomicMass"));
        } catch (SQLException e) {
            throw new ElementNotFoundException("Cannot find ID in Element table!", e);
        }
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
            throw new DataException("Cannot get Element ID from Element Table!", e);
        }

        return 0;
    }

    public long getId() {
        return id;
    }
    public long getAtomicNumber() {
        return atomicNumber;
    }
    public double getAtomicMass() {
        return atomicMass;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAtomicNumber(long atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public void setAtomicMass(double atomicMass) {
        this.atomicMass = atomicMass;
    }
}
