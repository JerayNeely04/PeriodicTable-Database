package datasource;

import java.sql.*;

public class SingleTableGateway {
    private long id;
    private String name;
    private int atomicNum;
    private double atomicMass;
    private long elementID;
    private long compoundID;
    private long solute;
    private long dissolvedBy;
    private long dissolves;
    private Connection connection;

    // finder constructor
    public SingleTableGateway(long id) throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM SingleTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.name = results.getString("name");
            this.atomicNum = results.getInt("atomicNum");
            this.atomicMass = results.getDouble("atomicMass");
            this.elementID = results.getLong("elementID");
            this.compoundID = results.getLong("compoundID");
            this.solute = results.getLong("solute");
            this.dissolvedBy = results.getLong("dissolvedBy");
            this.dissolves = results.getLong("dissolves");

        } catch (SQLException e) {
            System.out.println("Failed to create gateway");
        }
    }

    public static void createTable() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS SingleTable";
        String createStatement =
                "CREATE TABLE SingleTable (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(25), atomicNum INT, atomicMass DOUBLE," +
                        " elementID BIGINT, compoundID BIGINT, solute BIGINT, dissolvedBy BIGINT, " +
                        " dissolves BIGINT)";

        try {
            // drop old table
            PreparedStatement stmt;
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

    public static SingleTableGateway createChemical(String name) throws SQLException {
        String query = "INSERT INTO SingleTable (name) VALUES (?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create chemical table failed");
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createAcid(String name, long solute, long dissolves) throws SQLException {
        String query = "INSERT INTO SingleTable (name, solute, dissolves) VALUES (?, ?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setLong(2, solute);
            stmt.setLong(3, dissolves);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create acid table failed");
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createCompound(String name, long compoundID, long elementID) throws SQLException {
        String query = new String("INSERT INTO SingleTable (name, compoundID, elementID) VALUES (?, ?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setLong(2, compoundID);
            stmt.setLong(3, elementID);
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create compound table failed");
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createBase(String name, long solute) throws SQLException {
        String query = new String("INSERT INTO SingleTable (name, solute) VALUES (?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setLong(2, solute);
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create base table failed");
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createElement(String name, int atomicNum, double atomicMass) throws SQLException {
        String query = new String("INSERT INTO SingleTable (name, atomicNum, atomicMass) VALUES (?, ?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, atomicNum);
            stmt.setDouble(3, atomicMass);
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create base table failed");
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createMetal(String name, int atomicNum, double atomicMass, long dissolvedBy) throws SQLException {
        String query = new String("INSERT INTO SingleTable (name, atomicNum, atomicMass, dissolvedBy) VALUES (?, ?, ?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, atomicNum);
            stmt.setDouble(3, atomicMass);
            stmt.setLong(4, dissolvedBy);
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            // throw exception later
            System.out.println("Create base table failed");
        }

        return new SingleTableGateway(id);
    }

    public void persist() {
        String query = new String("UPDATE SingleTable SET name = ?, atomicNum = ?, atomicMass = ?, solute = ?, compoundID = ?, elementID = ?, dissolvedBy = ?, dissolves = ?");

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.atomicNum);
            stmt.setDouble(3, this.atomicMass);
            stmt.setLong(4, this.solute);
            stmt.setLong(5, this.compoundID);
            stmt.setLong(6, this.elementID);
            stmt.setLong(7, this.dissolvedBy);
            stmt.setLong(8, this.dissolves);

            stmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    /**
     * Tries to return the ID value for the new row being added
     *
     * @param stmt the prepared statement we're pulling the ID from
     * @return the ID of the row being added
     */
    private static long getIDFromDatabase(PreparedStatement stmt) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public void delete() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtomicNum() {
        return atomicNum;
    }

    public void setAtomicNum(int atomicNum) {
        this.atomicNum = atomicNum;
    }

    public double getAtomicMass() {
        return atomicMass;
    }

    public void setAtomicMass(double atomicMass) {
        this.atomicMass = atomicMass;
    }

    public long getElementID() {
        return elementID;
    }

    public void setElementID(long elementID) {
        this.elementID = elementID;
    }

    public long getSolute() {
        return solute;
    }

    public void setSolute(long solute) {
        this.solute = solute;
    }

    public long getDissolvedBy() {
        return dissolvedBy;
    }

    public void setDissolvedBy(long dissolvedBy) {
        this.dissolvedBy = dissolvedBy;
    }

    public long getDissolves() {
        return dissolves;
    }

    public void setDissolves(long dissolves) {
        this.dissolves = dissolves;
    }
}
