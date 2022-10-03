package datasource;

import java.sql.*;
import java.util.ArrayList;
import gatewayDTOs.Acid;

public class tableAcidGateway {
    private final Connection conn = null;
    private Acid acidDTO;

    //public tableAcidGateway() {}

    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS AcidTable";
        String createStatement =
                "CREATE TABLE AcidTable ("
                    + "solute   BIGINT PRIMARY KEY"
                + ")";

        try {
            PreparedStatement stmt;

            // drop old table
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

    public void CreateAcid(ResultSet rs) {
        try {
            this.acidDTO = new Acid(rs.getLong("solute"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return null;
    }

    public ArrayList<Acid> FindByAcid(long id, String name, long solute) {
        ArrayList<Acid> acid = new ArrayList<Acid>();

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = null;
            String query = "SELECT * FROM ClassTable WHERE solute = " + solute;

            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Acid ac = createAcid(rs);
                acid.add(ac);
            }

            rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return acid;
    }

    // Update table with info for a specific acid
    public void persist() {
        String query = "UPDATE GatewayDTOs.Acid"
             + "SET name = ?,"
             + "solute = ? WHERE solute = " + acidDTO.getSolute();

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setLong(1, acidDTO.getSolute());

            ps.executeUpdate();

            // Idk where to go from here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(long solute) {
        String query = "DELETE FROM AcidTable WHERE solute = " + solute;

        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the table!");
        }

        return false;
    }

    public boolean insertRow(Acid acid) {
        String query = "INSERT INTO classTable VALUES (?, ?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(3, acid.getSolute());

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: Couldn't insert acid into table");
        }

        return false;
    }
}



