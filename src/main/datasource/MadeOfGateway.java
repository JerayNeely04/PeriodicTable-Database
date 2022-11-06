package datasource;

import java.sql.*;
import java.util.ArrayList;

import gatewayDTOs.ElementDTO;
import gatewayDTOs.MadeOfDTO;

public class MadeOfGateway {
    private long compoundID;
    private long elementID;

    /**
     * creates a new MadeOf row
     *
     * @param compoundID the compound id
     * @param elementID  the element id
     * @throws DataException the database exception
     */
    public MadeOfGateway(long compoundID, long elementID) throws DataException {
        this.compoundID = compoundID;
        this.elementID = elementID;

        this.createRow(compoundID, elementID);
    }

    /**
     * Creates the madeOfTable in the database
     */
    public static void createTable() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query =
                "CREATE TABLE MadeOfTable ("
                        + "compoundID BIGINT NOT NULL,"
                        + "elementID BIGINT NOT NULL,"
                        + "FOREIGN KEY (elementID) REFERENCES ElementTable(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                        + "FOREIGN KEY (compoundID) REFERENCES CompoundTable(id) ON DELETE CASCADE ON UPDATE CASCADE)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.execute();

        } catch (SQLException e) {
            throw new DataException("Could not create MadeOf table", e);
        }
    }

    /**
     * Find all the records in the madeOfTable
     *
     * @return resultSet from the rows in the table
     * Return null is nothing is found or exception is thrown
     * @throws SQLException - for when either the connection or the query failed
     */
    public static ArrayList<MadeOfDTO> findAll() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MadeOfTable";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ArrayList<MadeOfDTO> madeOfList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MadeOfDTO MadeOf = createMadeOfDTO(rs);
                madeOfList.add(MadeOf);
            }
            return madeOfList;

        } catch (SQLException e) {
            throw new DataException("Could not fetch all MadeOf rows", e);
        }
    }

    /**
     * Finds all rows that match the specified CompoundID
     *
     * @param compoundID The compoundID to be found
     * @return ResultSet containing the row.
     * Return null is nothing is found or exception is thrown
     */
    public static ArrayList<MadeOfDTO> findByCompoundID(long compoundID) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MadeOfTable WHERE compoundID = " + compoundID;

        ArrayList<MadeOfDTO> elementsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                MadeOfDTO element = createMadeOfDTO(results);
                elementsList.add(element);
            }

            return elementsList;
        } catch (SQLException e) {
            throw new DataException("Could not find row by compound ID", e);
        }
    }

    /**
     * Finds all rows that match the specified ElementID
     *
     * @param elementID The elementID to be found
     * @return ResultSet containing the row.
     * Return null is nothing is found or exception is thrown
     */
    public static ArrayList<MadeOfDTO> findByElementID(long elementID) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM MadeOfTable WHERE elementID = " + elementID;

        ArrayList<MadeOfDTO> compounds = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                MadeOfDTO element = createMadeOfDTO(results);
                compounds.add(element);
            }

            return compounds;
        } catch (SQLException e) {
            throw new DataException("Could not find MadeOf row by elementID", e);
        }
    }

    /**
     * Inserts a row into the database
     *
     * @param compoundID The compoundID to insert
     *                   CompoundID must exist in ChemicalTable to succeed
     * @param elementID  The elementID to insert
     *                   ElementID must exist in the ElementTable to succeed
     */
    public void createRow(long compoundID, long elementID) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO MadeOfTable VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, compoundID);
            stmt.setLong(2, elementID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Could not insert row into MadeOf table", e);
        }
    }

    /**
     * Creates a new DTO for a MadeOf row
     *
     * @param rs the result set containing the MadeOf rows data
     * @return new MadeOf DTO
     */
    public static MadeOfDTO createMadeOfDTO(ResultSet rs) {
        try {
            long compoundID = rs.getLong("compoundID");
            long elementID = rs.getLong("elementID");

            return new MadeOfDTO(compoundID, elementID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the compoundID
     *
     * @return The compoundID
     */
    public long getCompoundID() {
        return compoundID;
    }

    /**
     * Sets the compoundID
     *
     * @param compoundID New value for the compoundID
     */
    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
    }

    /**
     * Get the elementID
     *
     * @return the elementID
     */
    public long getElementID() {
        return elementID;
    }

    /**
     * Sets the elementID
     *
     * @param elementID New value for elementID
     */
    public void setElementID(long elementID) {
        this.elementID = elementID;
    }
}
