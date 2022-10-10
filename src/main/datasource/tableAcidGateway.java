package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.Acid;

public class tableAcidGateway {
    private Connection conn = null;
    private long solute;
    //protected Acid acidDTO;

    public tableAcidGateway(long solute) {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.solute = solute;
        this.insertRow(solute);
    }

    public static void createTable() {
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
            e.printStackTrace();
        }
    }

    public long getSolute() {
        return solute;
    }

    public static Acid createAcid(ResultSet rs) {
        try {
            long solute = rs.getLong("solute");

            return new Acid(solute);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Acid> findAll() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM AcidTable ORDER BY solute";
        ArrayList<Acid> acidsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Acid acid = createAcid(results);
                acidsList.add(acid);
            }

            return acidsList;
        } catch (SQLException e)
        {
            System.out.println("Could not fetch all acids");
        }

        return null;
    }

    public static Acid findBySolute(long solute) {
        String query = "SELECT * FROM AcidTable WHERE solute = " + solute;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return createAcid(rs);
            }

            rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    public boolean persist() {
        String query = "UPDATE AcidTable "
                + "SET solute = ? WHERE solute = " + solute;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setLong(1, solute);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete() {
        String query = "DELETE FROM AcidTable WHERE solute = " + solute;

        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the table!");
        }

        return false;
    }

    public void insertRow(long solute) {
        String query = "INSERT INTO AcidTable VALUES (?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, solute);

            int n = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: Couldn't insert acid into table");
        }
    }
}