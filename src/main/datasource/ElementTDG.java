package datasource;
import gatewayDTOs.ElementDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * singleton for the element table
 */
public class ElementTDG {
    private static ElementTDG singleton;

    /**
     * @return the only instance of the element TDG
     */
    public static ElementTDG getInstance() {
        if (singleton == null) {
            singleton = new ElementTDG();
        }
        return singleton;
    }

    /**
     * private constructor to create the singleton
     */
    private ElementTDG() {}

    /**
     * creates a new element table in the database
     */
    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS ElementTable";
        String createStatement = "CREATE TABLE ElementTable (" +
                "id BIGINT PRIMARY KEY, " +
                "name VARCHAR(40), " +
                "atomicNum BIGINT, " +
                "atomicMass DOUBLE)";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Could not create element table");
        }
    }

    /**
     * gets every entry in the element table
     * @return DTO containing the element data
     */
    public ArrayList<ElementDTO> findAll()
    {
        return null;
    }

    /**
     * @param atomicNum the atomic number of the element
     * @return the element row with the matching atomic number as a DTO
     */
    public ElementDTO findByAtomicNumber(long atomicNum)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNum = " + atomicNum;

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            long id = results.getLong("id");
            String name = results.getString("name");
            double atomicMass = results.getDouble("atomicMass");

            return new ElementDTO(id, name, atomicNum, atomicMass);

        } catch (SQLException e)
        {
            System.out.println("No element with atomic number: " + atomicNum + " found.");
        }

        return null;
    }

    /**
     * @param name of the element
     * @return the element row with the matching name as a DTO
     */
    public ElementDTO findByName(String name)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            long id = results.getLong("id");
            long atomicNum = results.getLong("atomicNum");
            double atomicMass = results.getDouble("atomicMass");

            return new ElementDTO(id, name, atomicNum, atomicMass);

        } catch (SQLException e)
        {
            System.out.println("No element with name: " + name + " found.");
        }

        return null;
    }

    /**
     * creates a new row in the element table
     */
    public void createRow(String name, long atomicNum, double atomicMass)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO ElementTable (id, name, atomicNum, atomicMass) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, KeyRDG.generateId());
            stmt.setString(2, name);
            stmt.setLong(3, atomicNum);
            stmt.setDouble(4, atomicMass);
            stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create element row failed");
        }
    }
}
