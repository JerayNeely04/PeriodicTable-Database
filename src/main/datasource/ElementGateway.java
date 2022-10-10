package datasource;
import gatewayDTOs.ElementDTO;

import java.sql.*;
import java.util.ArrayList;

public class ElementGateway {
    // Row data gateway
    private long id;
    private String name;
    private long atomicNum;
    private double atomicMass;
    private final Connection connection;

    /**
     * creates a new element object and adds the entry into the database
     * @param name of the element
     * @param atomicNum of the element
     * @param atomicMass of the element
     */
    public ElementGateway(String name, long atomicNum, double atomicMass) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataException("Could not set auto commit to false", e);
        }

        this.name = name;
        this.atomicNum = atomicNum;
        this.atomicMass = atomicMass;

        this.createRow(name, atomicNum, atomicMass);
    }

    /**
     * finds an existing element in the element table
     * @param id the id to search for
     * @throws DataException
     */
    public ElementGateway(long id) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataException("Could not set auto commit to false", e);
        }

        String query = "SELECT * FROM ElementTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.name = results.getString("name");
            this.atomicNum = results.getInt("atomicNum");
            this.atomicMass = results.getDouble("atomicMass");

        } catch (SQLException e) {
            throw new DataException("Could not find element with ID: " + id, e);
        }
    }

    /**
     * creates a new row in the element table
     * @param name of the element
     * @param atomicNum of the element
     * @param atomicMass of the element
     */
    public void createRow(String name, long atomicNum, double atomicMass) throws DataException
    {
        String query = "INSERT INTO ElementTable (id, name, atomicNum, atomicMass) VALUES (?, ?, ?, ?)";

        try {
            long id = KeyRowDataGateway.generateId();
            this.id = id;

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setLong(3, atomicNum);
            stmt.setDouble(4, atomicMass);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Create element row failed");
        }
    }

    /**
     * updates a row in the element table
     * @return true if the row was updated otherwise returns false
     */
    public boolean update() {
        String query = "UPDATE ElementTable SET name = ?, " +
                "atomicNum = ?, " +
                "atomicMass = ?" +
                "WHERE id = " + id;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setLong(2, atomicNum);
            stmt.setDouble(3, atomicMass);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Row update failed");
        }

        return false;
    }

    /**
     * deletes a row from the element table by id
     * @return true if the row was deleted otherwise returns false
     */
    public boolean delete() {
        String query = "DELETE FROM ElementTable WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Row deletion failed");
        }

        return false;
    }

    /**
     * @return the row id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name of the element
     */
    public String getName() {
        return name;
    }

    /**
     * changes the name of the element
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the elements atomic number
     */
    public long getAtomicNum() {
        return atomicNum;
    }

    /**
     * changes the elements atomic number
     * @param atomicNum new atomic number
     */
    public void setAtomicNum(long atomicNum) {
        this.atomicNum = atomicNum;
    }

    /**
     * @return the elements atomic mass
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * changes the elements atomic mass
     * @param atomicMass the new atomic mass
     */
    public void setAtomicMass(long atomicMass) {
        this.atomicMass = atomicMass;
    }

    // Table data gateway

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
    public static ArrayList<ElementDTO> findAll()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM Element Table ORDER BY id";
        ArrayList<ElementDTO> elementsList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                ElementDTO element = createElementRecord(results);
                elementsList.add(element);
            }

            return elementsList;
        } catch (SQLException e)
        {
            System.out.println("Could not fetch all elements");
        }

        return null;
    }

    /**
     * @param atomicNum the atomic number of the element
     * @return the element row with the matching atomic number as a DTO
     */
    public static ElementDTO findByAtomicNumber(long atomicNum)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE atomicNum = " + atomicNum;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createElementRecord(results);

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
    public static ElementDTO findByName(String name)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM ElementTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createElementRecord(results);

        } catch (SQLException e)
        {
            System.out.println("No element with name: " + name + " found.");
        }

        return null;
    }

    /**
     * creates a new element DTO using a queries results
     * @param results the results given back from the query
     * @return the element DTO
     */
    private static ElementDTO createElementRecord(ResultSet results)
    {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");
            long atomicNum = results.getLong("atomicNum");
            double atomicMass = results.getDouble("atomicMass");

            return new ElementDTO(id, name, atomicNum, atomicMass);
        } catch (SQLException e)
        {
            System.out.println("Could not create element DTO");
        }

        return null;
    }
}
