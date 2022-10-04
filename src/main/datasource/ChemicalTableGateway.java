package datasource;

import gatewayDTOs.Chemical;
import gatewayDTOs.Element;

import java.sql.*;
import java.util.ArrayList;

public class ChemicalTableGateway {

    protected Chemical chemicalDTO;
    private Connection connection = null;

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

    public static void createTable() throws DataException {
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
            throw new DataException("Failed to create Chemical Table!", e);
        }
    }

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

    public static ChemicalTableGateway findById(long id) throws DataException {
        return new ChemicalTableGateway(id);
    }

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
