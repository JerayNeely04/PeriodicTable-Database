package datasource;

import gatewayDTOs.BaseDTO;
import java.sql.*;
import java.util.ArrayList;

public class BaseTableGateway {
    private final long solute;
    private final Connection conn;

    /**
     * The Constructor for Base Table Gateway
     * @param solute
     */
    public BaseTableGateway(long solute) throws DataException {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.solute = solute;
        this.insertRow(solute);
    }

    /**
     * Creates the Base Table in the database
     */
    public static void createTable() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String createStatement = "CREATE TABLE BaseTable (" +
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
     * getter for solute
     * @return solute
     */
    public long getSolute() { return solute; }

    /**
     * Create the Base DTO with the information from ResultSet
     * @param rs containing the info for the DTO
     * @return the new DTO
     */
    public static BaseDTO createBase(ResultSet rs) throws DataException {
        try {
            long solute = rs.getLong("solute");
            return new BaseDTO(solute);
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Find all Base from the Base Table
     * @return an array list with all the DTO's of the Base Table
     */
    public static ArrayList<BaseDTO> findAll() throws DataException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable ORDER BY solute";
        ArrayList<BaseDTO> baseList = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                BaseDTO base = createBase(rs);
                baseList.add(base);
            }
            return baseList;
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * find constructor that uses the solute to find the Base
     * @param solute
     * @return the new constructor with the specified solute
     * @throws DataException
     */
    public static BaseDTO findSolute(long solute) throws DataException {
        String query = "SELECT * FROM BaseTable WHERE solute = " + solute;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createBase(rs);
            }

            rs.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return null;
    }
    /**
     * Delete the current row for the Base table
     * @return a boolean if one row is affected after we execute the query
     * @throws DataException
     */
    public boolean delete() throws DataException {
        String query = "DELETE FROM BaseTable WHERE solute = " + solute;
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return false;
    }
    /**
     * Updating the current Base in the Base Table
     * @return true if changes were made
     */
    public boolean persist() throws DataException {
        String query = "UPDATE BaseTable SET solute = ? WHERE solute =" + solute;
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setLong(1, solute);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return false;
    }

    /**
     * insert row into Base Table
     * @param solute value insert into Base Table
     */
    public void insertRow(long solute) throws DataException {
        String query = "INSERT INTO BaseTable VALUES (?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, this.solute);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}