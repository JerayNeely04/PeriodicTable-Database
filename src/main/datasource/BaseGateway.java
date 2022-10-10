package datasource;
import gatewayDTOs.BaseDTO;

import java.sql.*;
import java.util.ArrayList;

public class BaseGateway {
    // Row data gateway
    private long id;
    private String name;
    private long solute;
    private final Connection connection;

    /**
     * creates a new base object and adds the entry into the database
     * @param name of the element
     * @param solute of the element
     * @throws DataException the database exception
     */
    public BaseGateway(String name, long solute) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.name = name;
        this.solute = solute;

        this.createRow(name, solute);
    }

    /**
     * finds an existing base in the base table
     * @param id the id to search for
     * @throws DataException the database exception
     */
    public BaseGateway(long id) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.name = results.getString("name");
            this.solute = results.getLong("solute");
        } catch (SQLException e) {
            throw new DataException("Could not find base with ID: " + id, e);
        }
    }

    /**
     * creates a new row in the base table
     * @param name of the element
     * @param solute of the element
     * @throws DataException the database exception
     */
    public void createRow(String name, long solute) throws DataException
    {
        String query = "INSERT INTO BaseTable (id, name, solute) VALUES (?, ?, ?)";

        try {
            long id = KeyRowDataGateway.generateId();
            this.id = id;

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setLong(3, solute);
            stmt.executeUpdate();

        } catch (SQLException e) {
           throw new DataException("Create base row failed", e);
        }
    }

    /**
     * updates a row in the base table
     * @return true if the row was updated otherwise returns false
     * @throws DataException the database exception
     */
    public boolean update() throws DataException {
        String query = "UPDATE BaseTable SET name = ?, " +
                "solute = ? " +
                "WHERE id = " + id;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setLong(2, solute);

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataException("Base row update failed", e);
        }

        return false;
    }

    /**
     * deletes a row from the base table by id
     * @return true if the row was deleted otherwise returns false
     */
    public void delete() throws DataException {
        String query = "DELETE FROM BaseTable WHERE id = " + id;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Base row could not be deleted", e);
        }
    }

    /**
     * @return the row id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name of the base
     */
    public String getName() {
        return name;
    }

    /**
     * changes the name of the base
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the bases solute
     */
    public long getSolute() {
        return solute;
    }

    /**
     * changes the bases solute
     * @param solute new solute
     */
    public void setSolute(long solute) {
        this.solute = solute;
    }

    // Table data gateway

    /**
     * creates a new base table in the database
     * @throws DataException the database exception
     */
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS BaseTable";
        String createStatement = "CREATE TABLE BaseTable (" +
                "id BIGINT PRIMARY KEY, " +
                "name VARCHAR(40), " +
                "solute BIGINT)";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
        } catch (SQLException e) {
           throw new DataException("Could not create base table", e);
        }
    }

    /**
     * gets every entry in the base table
     * @return DTO containing the base data
     * @throws DataException the database exception
     */
    public static ArrayList<BaseDTO> findAll() throws DataException
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable ORDER BY id";
        ArrayList<BaseDTO> basesList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                BaseDTO base = createRecord(results);
                basesList.add(base);
            }

            return basesList;
        } catch (SQLException e)
        {
            throw new DataException("Could not fetch all bases", e);
        }
    }

    /**
     * @param solute the atomic number of the element
     * @return the element row with the matching atomic number as a DTO
     * @throws DataException the database exception
     */
    public static ArrayList<BaseDTO> findAllBasesSolubleBy(long solute) throws DataException
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable WHERE solute = " + solute;
        ArrayList<BaseDTO> basesList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                BaseDTO base = createRecord(results);
                basesList.add(base);
            }

            return basesList;

        } catch (SQLException e)
        {
            throw new DataException("No bases with solute id: " + solute + " found.", e);
        }
    }

    /**
     * @param name of the element
     * @return the element row with the matching name as a DTO
     * @throws DataException the database exception
     */
    public static BaseDTO findByName(String name) throws DataException
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM BaseTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createRecord(results);

        } catch (SQLException e)
        {
            throw new DataException("No base with name: " + name + " found.", e);
        }
    }

    /**
     * deletes all records from the base table where solute is equal to the other tables deleted row id
     * @param solute the solute value
     * @throws DataException the database exception
     */
    public static void deleteAllFromForeignReference(long solute) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM BaseTable WHERE solute = " + solute;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Base rows could not be deleted", e);
        }
    }

    /**
     * creates a new element DTO using a queries results
     * @param results the results given back from the query
     * @return the element DTO
     * @throws DataException the database exception
     */
    private static BaseDTO createRecord(ResultSet results) throws DataException
    {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");
            long solute = results.getLong("solute");

            return new BaseDTO(id, name, solute);
        } catch (SQLException e)
        {
           throw new DataException("Could not create base DTO", e);
        }
    }
}