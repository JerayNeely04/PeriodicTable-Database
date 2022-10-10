package datasource;

import gatewayDTOs.Element;

import java.sql.*;
import java.util.ArrayList;

public class ElementTableGateway {

    protected Element elementDTO;               /* I used this to act as a DTO and hold info for the current Element */
    private Connection connection = null;

    /**
     * The constructor for the Element table Gateway
     * @param atomicNumber the primary key of the Element table
     * @throws DataException SQL exception if we failed to SELECT FROM the Element Table!
     */
    public ElementTableGateway(long atomicNumber) throws DataException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNumber = " + atomicNumber;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            elementDTO = new Element(atomicNumber,results.getDouble("atomicMass"));
        } catch (SQLException e) {
            throw new DataException("Failed to Select Element gateway!", e);
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
            // create new table
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
    public static ElementTableGateway createElement(long atomicNumber, double atomicMass) throws DataException {
        String query = "INSERT INTO ElementTable (atomicNumber, atomicMass) VALUES (?,?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, atomicNumber);
            stmt.setDouble(2, atomicMass);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException("Cannot crate an Element in the Element Table!", e);
        }
        return new ElementTableGateway(atomicNumber);
    }

    /**
     * Updating the current Element in the Element table.
     * @throws DataException SQL Exception if we failed to update to the element table.
     */
    public void persist() throws DataException {
        String query = "UPDATE ElementTable SET atomicMass = ? WHERE atomicNumber = " + elementDTO.getAtomicNumber();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setDouble(1, elementDTO.getAtomicMass());
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
        String query = "DELETE FROM ElementTable WHERE atomicNumber = " + elementDTO.getAtomicNumber();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
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
    public static ElementTableGateway findByAtomicNumber(long atomicNum) throws DataException {
        return new ElementTableGateway(atomicNum);
    }

    /**
     * Find all the element from the element table.
     * @return an array list with all the DTO's of the Element table.
     * @throws DataException SQL exception if we cannot find all the elements.
     */
    public static ArrayList<Element> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable ORDER BY atomicNumber";
        ArrayList<Element> elementsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Element element = createElementRecord(results);
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
    private static Element createElementRecord(ResultSet results) throws DataException {
        try {
            long atomicNum = results.getLong("atomicNumber");
            double atomicMass = results.getDouble("atomicMass");

            return new Element(atomicNum, atomicMass);
        } catch (SQLException e)
        {
            throw new DataException("Could not create element DTO!", e);
        }
    }
}
