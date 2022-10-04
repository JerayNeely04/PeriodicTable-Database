package datasource;

import gatewayDTOs.Base;
import java.sql.*;
import java.util.ArrayList;

public class BaseTableGateway {
    private final long solute;
    private final Connection connection;
    public BaseTableGateway(long solute)
    {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.solute = solute;
        this.insertRow(solute);
    }
    public static void createTable() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS BaseTable";
        String createStatement = "CREATE TABLE BaseTable " +
                "(solute BIGINT, " +
                "FOREIGN KEY (solute) REFERENCES chemical(solute))";
        try {
            PreparedStatement stmt;
            // drop old table
            stmt = connection.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            // create new table
            stmt = connection.prepareStatement(createStatement);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public long getSolute() { return solute; }
    public static Base createBase(ResultSet rs) {
        try {
            long solute = rs.getLong("solute");
            return new Base(solute);
        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create base table failed");
        }
        return null;
    }

    public static ArrayList<Base> findAll() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable ORDER BY solute";
        ArrayList<Base> baseList = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Base base = createBase(rs);
                baseList.add(base);
            }
            return baseList;
        } catch (SQLException e) {
            System.out.println("Could not fetch all base");
        }
        return null;
    }

    public static Base findSolute(long solute) {
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
            e.printStackTrace();
        }
        return null;
    }
    public boolean delete() {
        String query = "DELETE FROM BaseTable WHERE solute = " + solute;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete a row in the table!");
        }
        return false;
    }
    public boolean persist() {
        String query = "UPDATE BaseTable SET solute = ? WHERE solute =" + solute;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setLong(1, this.solute);
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Data not persisted to the database.");
        }
        return false;
    }
    public void insertRow(long solute) {
        String query = "INSERT INTO BaseTable VALUES (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, this.solute);

            int n = stmt.executeUpdate();
            if(n > 0) {
                System.out.println("Insert SUCCEEDED with " + n + " affected rows");
            } else {
                System.out.println("Insert FAILED");
            }
        } catch (SQLException e) {
            System.out.println("Error: Couldn't insert base into table");
        }
    }
}