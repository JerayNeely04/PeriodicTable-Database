package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.AcidDTO;

public class AcidTableGateway {
    private Connection conn = null;
    private long id;
    private long solute;

    /**
     * Create new instance of AcidTableGateway
     *
     * @param solute Solute value to set to instance variable
     */
    public AcidTableGateway(long id, long solute) throws DataException {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.id = id;
        this.solute = solute;
        this.insertRow(id, solute);
    }

    /**
     * Creates the AcidTable in the database
     */
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query =
                "CREATE TABLE AcidTable ("
                        + "solute   BIGINT,"
                        + "FOREIGN KEY (solute) REFERENCES ChemicalTable(id) ON DELETE CASCADE"
                + ")";

        try {
            PreparedStatement stmt;

            // create new table
            stmt = conn.prepareStatement(query);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Get solute value
     *
     * @return the solute value from instance
     */
    public long getSolute() {
        return solute;
    }

    /**
     * Get id value
     *
     * @return the solute value from instance
     */
    public long getId() {
        return id;
    }

    /**
     * Create Acid DTO with information from ResultSet
     *
     * @param rs   The ResultSet containing the info for the DTO
     * @return the new DTO
     */
    public static AcidDTO createAcid(ResultSet rs) throws DataException {
        try {
            long solute = rs.getLong("solute");
            long id = rs.getLong("id");

            return new AcidDTO(id, solute);
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Finds all rows in the AcidTable and orders them by solute
     *
     * @return Array of all rows in the AcidTable
     */
    public static ArrayList<AcidDTO> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM AcidTable ORDER BY solute";
        ArrayList<AcidDTO> acidsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                AcidDTO acid = createAcid(results);
                acidsList.add(acid);
            }

            return acidsList;
        } catch (SQLException e)
        {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Finds the row(s) with a certain solute
     *
     * @param solute The solute to find
     * @return Row(s) containing the solute value
     */
    public static AcidDTO findBySolute(long solute) throws DataException {
        String query = "SELECT * FROM AcidTable WHERE solute = " + solute;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return createAcid(rs);
            }

            rs.close();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return null;
    }

    /**
     * Update the database with the updated information
     *
     * @return true if update was successful; false otherwise
     */
    public boolean persist() throws DataException {
        String query = "UPDATE AcidTable "
                + "SET solute = ?, id = ? WHERE solute = " + solute;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setLong(1, solute);
            stmt.setLong(2, id);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return false;
    }

    /**
     * Delete 1 or more rows in the database where solute is a certain value
     *
     * @return true if DELETE deleted 1+ rows; false otherwise
     */
    public boolean delete() throws DataException {
        // In case this fails, it was DELETE FROM AcidTable WHERE solute = solute
        // Delete this comment is the test passed
        String query = "DELETE FROM AcidTable WHERE id = " + id;

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
     * Insert a row into the AcidTable
     *
     * @param solute The solute value to insert into the AcidTable
     */
    public void insertRow(long id, long solute) throws DataException {
        String query = "INSERT INTO AcidTable VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, solute);
            stmt.setLong(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }
}