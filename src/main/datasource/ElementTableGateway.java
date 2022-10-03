package datasource;

import gatewayDTOs.Element;

import java.sql.*;
import java.util.ArrayList;

public class ElementTableGateway {

    protected Element elementDTO;
    private Connection connection = null;
    public ElementTableGateway(long atomicNumber) {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNumber = " + atomicNumber;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            elementDTO = new Element(atomicNumber,results.getDouble("atomicMass"));
        } catch (SQLException e) {
            System.out.println("Failed to create Element gateway!");
        }

    }
    public static void createTable() {
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
            e.printStackTrace();
        }
    }

    public static ElementTableGateway createElement(long atomicNumber, double atomicMass) throws SQLException {
        String query = "INSERT INTO ElementTable (atomicNumber, atomicMass) VALUES (?,?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, atomicNumber);
            stmt.setDouble(2, atomicMass);

            stmt.executeUpdate();
        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create Element failed!");
        }
        return new ElementTableGateway(atomicNumber);
    }

    public void persist() {
        String query = "UPDATE ElementTable SET atomicMass = ? WHERE atomicNumber = " + elementDTO.getAtomicNumber();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setDouble(1, elementDTO.getAtomicMass());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Data not persisted to the Element table!");
        }
    }

    public boolean delete() {
        String query = "DELETE FROM ElementTable WHERE atomicNumber = " + elementDTO.getAtomicNumber();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the Element table!");
        }
        return false;
    }

    public static ElementTableGateway findByAtomicNumber(long atomicNum) {
        return new ElementTableGateway(atomicNum);
    }

    public static ArrayList<Element> findAll()
    {
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
            System.out.println("Could not fetch all elements for the Class Table!");
        }
        return null;
    }

    private static Element createElementRecord(ResultSet results) {
        try {
            long atomicNum = results.getLong("atomicNumber");
            double atomicMass = results.getDouble("atomicMass");

            return new Element(atomicNum, atomicMass);
        } catch (SQLException e)
        {
            System.out.println("Could not create element DTO!");
        }
        return null;
    }
}
