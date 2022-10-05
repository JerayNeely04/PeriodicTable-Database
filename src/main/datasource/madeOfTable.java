package datasource;

import java.sql.*;

public class madeOfTable {
    private long compoundID;
    private long elementID;

    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS madeOfTable";
        String query =
                "CREATE TABLE madeOfTable ("
                        + "compoundID BIGINT NOT NULL,"
                        + "elementID BIGINT NOT NULL,"
                        + "FOREIGN KEY (elementID) REFERENCES ElementTable(atomicNumber) ON DELETE CASCADE,"
                        + "FOREIGN KEY (compoundID) REFERENCES ChemicalTable(id) ON DELETE CASCADE"
                + ")";

       try {
            PreparedStatement stmt;

            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(query);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRow(long compoundID, long elementID) {
        String query = "INSERT INTO madeOf VALUES (?, ?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setLong(1, compoundID);
            stmt.setLong(2, elementID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCompoundID() {
        return compoundID;
    }

    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
    }

    public long getElementID() {
        return elementID;
    }

    public void setElementID(long elementID) {
        this.elementID = elementID;
    }
}