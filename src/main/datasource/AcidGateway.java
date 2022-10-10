package datasource;

import gatewayDTOs.AcidDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AcidGateway {
    private long id;
    private String name;
    private long solute;
    private final Connection connection;

    public AcidGateway(String name, long solute)throws DataException{

        this.connection = DatabaseConnection.getInstance().getConnection();
        this.name = name;
        this.solute =solute;
        this.createRow();
    }

    public AcidGateway(long id)throws DataException{
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM AcidTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.name = results.getString("name");
            this.solute = results.getLong("solute");
        } catch (SQLException e) {
            throw new DataException("Could not find base with ID: " +  id, e);
        }

    }

    /**
     * creates a new role values id,name, and solute
     * @throws DataException database exception
     */
    private void createRow() throws DataException {

        String query = "INSERT INTO AcidTable (id,name,solute) VALUES (?,?,?)";

        try{
            long id = KeyRowDataGateway.generateId();
            this.id = id;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setLong(3, solute);
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new DataException("Create Acid row failed", e);
        }
    }

    /**
     *
     * @return updates the row in the acid table.
     * return true if the row was updated, if not return false.
     * @throws DataException
     */
    public boolean update() throws DataException {
        String query = "UPDATE AcidTable SET name = ?, " +
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
            throw new DataException("Acid row update failed", e);
        }

        return false;
    }

    /**
     *  deletes the row that was inserted into the database.
     * @throws DataException
     * @return
     */
    public void delete() throws DataException {
        String query = "DELETE FROM AcidTable WHERE id = " + id;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Acid row could not be deleted", e);
        }
    }

    /**
     * creates a new Acid table within the database.
     * @throws DataException
     */
    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS AcidTable";
        String createStatement = "CREATE TABLE AcidTable (" +
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
            throw new DataException("Could not create Acid table", e);
        }
    }

    /**
     * find every entry that is within the table.
     * @return the Acid DTO containing the data base.
     * @throws DataException
     */
        public static ArrayList<AcidDTO> findAll()throws DataException
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String Query = "SELECT * FROM AcidTable ORDER BY id";

        ArrayList<AcidDTO> AcidList = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(Query);
            ResultSet results = stmt.executeQuery();

            while(results.next()){
                AcidDTO Acid = createRecord(results);
                AcidList.add(Acid);
            }
            return AcidList;
        } catch (SQLException e) {
            throw new DataException("Could not fetch all Acids", e);
        }

    }

    public static ArrayList<AcidDTO> findBySolute(long solute)throws DataException{
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM AcidTable WHERE solute = " + solute;
        ArrayList<AcidDTO> AcidList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                AcidDTO Acid = createRecord(results);
                AcidList.add(Acid);
            }

            return AcidList;

        } catch (SQLException e)
        {
            throw new DataException("No Acid with solute id: " + solute + " found.", e);
        }

    }

    public static AcidDTO findByName(String name) throws DataException{
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM AcidTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createRecord(results);

        } catch (SQLException e)
        {
           throw new DataException("No Acid with name: " + name + " found.");
        }


    }
    public static void deleteAllFromForeignReference(long solute)throws DataException{
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM AcidTable WHERE solute = " + solute;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataException("Acid rows could not be deleted", e);
        }

    }
    /**
     * Create a new element DTO using a query result
     * @param results that are given back from the query
     * @return the Acid DTO
     * @throws DataException
     */
    private static AcidDTO createRecord(ResultSet results)throws DataException {
            try{
                long id = results.getLong("id");
                String name = results.getString("name");
                long solute = results.getLong("solute");

                return new AcidDTO(id,name,solute);
            } catch (SQLException e) {
                throw new DataException("Could not create Acid DTO", e);
            }

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSolute(long solute) {
        this.solute = solute;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSolute() {
        return solute;
    }
}
