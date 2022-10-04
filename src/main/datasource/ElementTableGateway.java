package datasource;

import gatewayDTOs.Element;

import java.sql.*;
import java.util.ArrayList;

public class ElementTableGateway {

    protected Element elementDTO;
    private Connection connection = null;
    public ElementTableGateway(long atomicNumber) throws DataException {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNumber = " + atomicNumber;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            elementDTO = new Element(atomicNumber,results.getDouble("atomicMass"));
        } catch (SQLException e) {
            throw new DataException("Failed to create Element gateway!", e);
        }

    }
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS ElementTable";
        String createStatement =
                "CREATE TABLE ElementTable (atomicNumber BIGINT NOT NULL PRIMARY KEY, atomicMass DOUBLE NOT NULL)";

        try {
            // drop old table
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            // create new table
            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException("Cannot create Element Table!", e);
        }
    }

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

    public static ElementTableGateway findByAtomicNumber(long atomicNum) throws DataException {
        return new ElementTableGateway(atomicNum);
    }

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
