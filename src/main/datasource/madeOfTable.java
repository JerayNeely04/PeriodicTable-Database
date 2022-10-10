package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.madeOf;

import javax.xml.transform.Result;

public class madeOfTable {
    private long compoundID;
    private long elementID;

    /**
     * Creates the madeOfTable in the database
     */
    public static void createTable() throws SQLException {

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query =
                    "CREATE TABLE madeOfTable ("
                            + "compoundID BIGINT NOT NULL,"
                            + "elementID BIGINT NOT NULL,"
                            + "FOREIGN KEY (elementID) REFERENCES ElementTable(atomicNumber) ON DELETE CASCADE,"
                            + "FOREIGN KEY (compoundID) REFERENCES ChemicalTable(id) ON DELETE CASCADE"
                            + ")";
            PreparedStatement stmt;

            stmt = conn.prepareStatement(query);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static madeOf createMadeOf(ResultSet rs) {
        try {
            long compoundID = rs.getLong("compoundID");
            long elementID = rs.getLong("elementID");

            return new madeOf(compoundID, elementID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Find all the records in the madeOfTable
     *
     * @return resultSet from the rows in the table
     *         Return null is nothing is found or exception is thrown
     * @throws SQLException - for when either the connection or the query failed
     */
    public static ArrayList<madeOf> findAll() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        ArrayList<madeOf> madeOfList = new ArrayList<>();

        try {
            String query = "SELECT * FROM madeOfTable";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                madeOf MadeOf = createMadeOf(rs);
                madeOfList.add(MadeOf);
            }

            return madeOfList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds all rows that match the specified CompoundID
     *
     * @param compoundID The compoundID to be found
     * @return ResultSet containing the row.
     *         Return null is nothing is found or exception is thrown
     */
    public static madeOf findByCompoundID(long compoundID) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "SELECT * FROM madeOfTable WHERE compoundID = " + compoundID;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createMadeOf(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds all rows that match the specified ElementID
     *
     * @param elementID The elementID to be found
     * @return ResultSet containing the row.
     *         Return null is nothing is found or exception is thrown
     */
    public static madeOf findByElementID(long elementID) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        // TODO: If there are two rows with the specific elementID,
        //  how do we determine which one we want?
        //  Currently, this function is returning whatever the first row is
        //  that matches the elementID

        try {
            String query = "SELECT * FROM madeOfTable WHERE elementID = " + elementID;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createMadeOf(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts a row into the database
     *
     * @param compoundID The compoundID to insert
     *                   CompoundID must exist in ChemicalTable to succeed
     * @param elementID The elementID to insert
     *                  ElementID must exist in the ElementTable to succeed
     */
    public void insertRow(long compoundID, long elementID) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO madeOfTable VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setLong(1, compoundID);
            stmt.setLong(2, elementID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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