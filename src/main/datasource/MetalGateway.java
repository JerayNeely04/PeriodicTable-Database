package datasource;
import gatewayDTOs.MetalDTO;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * singleton for the element table
 */
public class MetalGateway {
    // Row data gateway
    private long id;
    private String name;
    private static long atomicNum;
    private double atomicMass;

    private final Connection connection;


    /**
     * private constructor to create the singleton
     */
    public MetalGateway(String name, long atomicNum, double atomicMass) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.name = name;
        this.atomicNum = atomicNum;
        this.atomicMass = atomicMass;
        this.createRow(name,atomicNum, atomicMass);
    }

    /**
     * Creates the table for metal in database
     */
    public static void createTable()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS MetalTable";
        String createStatement = "CREATE TABLE MetalTable (" +
                "id BIGINT PRIMARY KEY, " +
                "name VARCHAR(40), " +
                "atomicNum BIGINT, " +
                "atomicMass DOUBLE)";

        try(PreparedStatement stmt = conn.prepareStatement(createStatement)){

            stmt.execute();

        }catch(SQLException e){
            System.out.println("Metal table was not created");
        }
    }

    /**
     *
     * @return true if the table is able to update
     * if not then it will return false value
     */
    public boolean update(){
        String query = "UPDATE MetalTable SET name = ?, " +
                "atomicNum = ?, " +
                "atomicMass = ?" +
                "WHERE id = " + id;
        try(PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setString(1,name);
            stmt.setLong(2,atomicNum);
            stmt.setDouble(3,atomicMass);

            if(stmt.executeUpdate() > 0){
                System.out.println("Row was updated");
                return true;

            }

        } catch (SQLException e) {
            System.out.println("Row failed to update");
        }
        return false;
    }

    /**
     *
     * @return true is the row is able to be deleted
     * but return false if row failed to be deleted
     */
    public boolean delete(){
        String query = "DELETE  FROM MetalTable WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){

            stmt.setLong(1,id);

            if(stmt.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Row failed to delete");
        }
        return false;
    }

    /**
     * every entry in the element table
     * @return DTO containing the element data
     */
    public static ArrayList<MetalDTO> findAll()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String Query = "SELECT * FROM MetalTable ORDER BY id";
        ArrayList<MetalDTO> metalList = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(Query)){

            ResultSet results = stmt.executeQuery();

            while(results.next()){
                MetalDTO metal = createMetalRecord(results);
                metalList.add(metal);
            }
            return metalList;
        } catch (SQLException e) {
            System.out.println("Could not fetch all metals");
        }
        return null;
    }

    /**
     * @param atomicNumber the atomic number of the element
     * @return the element with the matching atomic number
     */
    public static MetalDTO findByAtomicNumber(long atomicNumber)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        String query = "SELECT * FROM MetalTable WHERE atomicNum = "+ atomicNum;

        try(PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet results = stmt.executeQuery();
            results.next();

            return createMetalRecord(results);
        }catch(SQLException e){
            System.out.println("No element with atomic number "+ atomicNum + "found ");
        }
        return null;
    }

    private static MetalDTO createMetalRecord(ResultSet results) {
        try{
            long id = results.getLong("id");
            String name = results.getString("name");
            long atomicNum = results.getLong("atomicNum");
            double atomicMass = results.getDouble("atomicMass");

            return new MetalDTO(id, name, atomicNum, atomicMass);
        }catch(SQLException e){
            System.out.println("Metal DTO was not created");
        }
        return null;
    }


    /**
     * @param name of the element
     * @return the element with the matching name
     */
    public static MetalDTO findByName(String name)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String Query = "SELECT * FROM MetalTable WHERE name =" + name + "";

        try(PreparedStatement stmt = conn.prepareStatement(Query)){

            ResultSet results =stmt.executeQuery();
            results.next();
            return createMetalRecord(results);

        } catch (SQLException e) {
            System.out.println("No Metal with name " + name + " found");
        }
        return null;
    }

    /**
     * creates a new row in the element table
     */
    public void createRow(String name, long atomicNum, double atomicMass)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO MetalTable (id,name,atomicNum,atomicMass) VALUES " +
                "(?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(query)){
            long id = KeyRowDataGateway.generateId();
            this.id = id;


            stmt.setLong(1,id);
            stmt.setString(2,name);
            stmt.setLong(3,atomicNum);
            stmt.setDouble(4,atomicMass);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create Metal row did not create");
        }
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAtomicNum(long atomicNum) {
        this.atomicNum = atomicNum;
    }

    public void setAtomicMass(double atomicMass) {
        this.atomicMass = atomicMass;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAtomicNum() {
        return atomicNum;
    }

    public double getAtomicMass() {
        return atomicMass;
    }

}

