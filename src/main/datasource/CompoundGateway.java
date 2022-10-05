package datasource;
import gatewayDTOs.CompoundDTO;

import java.sql.Connection;

import java.sql.*;
import java.util.ArrayList;

public class CompoundGateway {
    //Row data Gateways
    private long id;
    private String name;
    private final Connection connection;

    /**
     * Creates a new compound and adds it to the database
     * @param name the name
     */
    public CompoundGateway(String name) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.name = name;

        this.createRow(name);
    }

    /**
     * Finds a compound that already exists in the compound table
     * @param id, id that you are searching for
     * @throws DataException
     */
    public CompoundGateway(long id)throws DataException{
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "Select * from CompoundTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.name = results.getString("name");

        } catch (SQLException e) {
            throw new DataException("Could not find compound with ID: " + id, e);
        }
    }

    /**
     * This method will create a new row in the Compound table
     * @param name, the name of the element
     */
    private void createRow(String name) throws DataException {

        String query = "INSERT INTO CompoundTable (id, name) VALUES (?, ?)";

        try{
            long id = KeyRowDataGateway.generateId();
            this.id = id;

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create compound row failed");
        }
    }

    /**
     * This method will update a row in the compound table
     * @return it will return true if the row was updated. Otherwise, it will return false.
     */

    public boolean update()
    {
        String query = "UPDATE CompoundTable SET name = ? " +
                "WHERE id = " + id;
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);

            if (stmt.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * This method will delete a row from the compound table by ID.
     * @return  this method will return true if the row is deleted it will return false if otherwise
     */

    public boolean delete()
    {
        String query = "DELETE FROM CompoundTable WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            //stmt.executeUpdate();

            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("The row was not detected");
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

        //Table Data Gateway

    /**
     * Creates a new compound in the table
     */
    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS CompoundTable";
        String createStatement = "CREATE TABLE CompoundTable (" +
                "id BIGINT PRIMARY KEY, " +
                "name VARCHAR(40))";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(createStatement);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Could not create Compound table");
        }
    }

    /**
     * gets every entry in the compound table
     * @return DTO containing the compound data
     */
    public static ArrayList<CompoundDTO> findAll()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM CompoundTable ORDER BY id";
        ArrayList<CompoundDTO> compoundList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                CompoundDTO compound = createCompoundRecord(results);
                compoundList.add(compound);
            }

            return compoundList;
        } catch (SQLException e)
        {
            System.out.println("Could not fetch all elements");
        }

        return null;
    }

    /**
     *
     * @param name, name od the element
     * @return the compound row that has a matching name as a DTO
     */
    public static CompoundDTO findByName(String name)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM CompoundTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createCompoundRecord(results);

        } catch (SQLException e)

        {
            System.out.println("No element with name: " + name + " found.");
        }

        return null;
    }

    public static CompoundDTO findByAtomicNumber(long id)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM CompoundTable WHERE id = " + id;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createCompoundRecord(results);

        } catch (SQLException e)
        {
            System.out.println("No element with id: " + id + " found.");
        }

        return null;
    }

    /**
     * Creates a New DTO using queries results
     * @param results, the results given back from the query
     * @return, returns the compound DTO
     */
    private static CompoundDTO createCompoundRecord(ResultSet results)
    {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");

            return new CompoundDTO(id, name);
        } catch (SQLException e)
        {
            System.out.println("Could not create compound DTO");
        }

        return null;
    }

    }

