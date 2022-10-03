package datasource;

import gatewayDTOs.Element;

import java.sql.*;

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
        String query = "INSERT INTO ElementTable  (atomicNumber, atomicMass) VALUES (?,?)";

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
}
