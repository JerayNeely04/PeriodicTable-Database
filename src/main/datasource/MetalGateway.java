package datasource;
import gatewayDTOs.MetalDTO;

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
    private long atomicNum;
    private double atomicMass;

    private final Connection connection;


    /**
     * private constructor to create the singleton
     */
    private MetalGateway(long id, String name, long atomicNum, double atomicMass) {
      this.connection = DatabaseConnection.getInstance().getConnection();
      this.name = name;
      this.atomicNum = atomicNum;
      this.atomicMass = atomicMass;
      this.createRow(name,atomicNum, atomicMass);
    }

    /**
     * Creates the table for metal in database
     */
    public void createTable()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS MetalTable";
        String createStatement = "CREATE TABLE ElementTable (" +
                "id BIGINT PRIMARY KEY, " +
                "name VARCHAR(40), " +
                "atomicNum BIGINT, " +
                "atomicMass DOUBLE)";

        try{
            PreparedStatement stmt;
            stmt = conn.prepareStatement(dropStatement);
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(createStatement);
            stmt.execute();

        }catch(SQLException e){
            System.out.println("Metal table was not created");
        }
    }

    /**
     * every entry in the element table
     * @return DTO containing the element data
     */
    public ArrayList<MetalDTO> findAll()
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String Query = "SELECT * FROM MetalTable ORDER BY id";
        ArrayList<MetalDTO> metalList = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(Query);
            ResultSet results = stmt.executeQuery();

            while(results.next()){
                MetalDTO metal = createMentalRecord(results);
                metalList.add(metal);
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch all metals");
        }
        return null;
    }

    /**
     * @param atomicNumber the atomic number of the element
     * @return the element with the matching atomic number
     */
    public MetalDTO findByAtomicNumber(long atomicNumber)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        String query = "SELECT * FROM MetalTable WHERE atomincNumber ="+ atomicNumber;

        try{
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            return createMentalRecord(results);
        }catch(SQLException e){
            System.out.println("Metal table was not created");
        }
        return null;
    }

    private MetalDTO createMentalRecord(ResultSet results) {
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

    /**
     * @param name of the element
     * @return the element with the matching name
     */
    public MetalDTO findByName(String name)
    {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String Query = "SELECT * FROM MetalTable WHERE name =" + name + "";

        try{
            PreparedStatement stmt;
            stmt = conn.prepareStatement(Query);
            ResultSet results =stmt.executeQuery();
            results.next();
            return createMentalRecord(results);
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

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            stmt.setString(2,name);
            stmt.setLong(3,atomicNum);
            stmt.setDouble(4,atomicMass);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create Metal row did not create");
        }
    }
}

