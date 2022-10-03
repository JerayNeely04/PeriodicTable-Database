package datasource;

import gatewayDTOs.Chemical;

import java.sql.*;

public class ChemicalTableGateway {

    protected Chemical chemicalDTO;
    private Connection connection = null;

    public ChemicalTableGateway(long id) {
        connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ChemicalTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            chemicalDTO = new Chemical(id,results.getString("name"));
        } catch (SQLException e) {
            System.out.println("Failed to create Chemical gateway!");
        }

    }

    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS ChemicalTable";
        String createStatement =
                "CREATE TABLE ChemicalTable (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50))";

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

    public static ChemicalTableGateway createChemical(String name) throws SQLException {
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
            // throw exception later
            System.out.println("Create Chemical failed!");
        }
        return new ChemicalTableGateway(id);
    }



    private static long getIDFromDatabase(PreparedStatement stmt) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public void persist() {
        String query = "UPDATE ChemicalTable SET name = ? WHERE id = " + chemicalDTO.getId();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, chemicalDTO.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Data not persisted to the chemical table!");
        }
    }

    public boolean delete() {
        String query = "DELETE FROM ChemicalTable WHERE id = " + chemicalDTO.getId();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the chemical table!");
        }
        return false;
    }

}
