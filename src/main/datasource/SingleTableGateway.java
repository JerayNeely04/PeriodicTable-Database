package datasource;

import GatewayDTO.ElementDTO;

import java.sql.*;
import java.util.ArrayList;

public class SingleTableGateway {
    private long id;
    private String name;
    private long atomicNum;
    private double atomicMass;
    private long elementID;
    private long compoundID;
    private long solute;
    private long dissolvedBy;
    private long dissolves;
    private final Connection connection;

    // finder constructor
    public SingleTableGateway(long id) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM SingleTable WHERE id = " + id;

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.id = id;
            this.name = results.getString("name");
            this.atomicNum = results.getInt("atomicNum");
            this.atomicMass = results.getDouble("atomicMass");
            this.elementID = results.getLong("elementID");
            this.compoundID = results.getLong("compoundID");
            this.solute = results.getLong("solute");
            this.dissolvedBy = results.getLong("dissolvedBy");
            this.dissolves = results.getLong("dissolves");

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    // Find constructor for name
    public SingleTableGateway(String name) throws DataException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM SingleTable WHERE name = '" + name + "'";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            results.next();

            this.name = name;
            this.id = results.getLong("id");
            this.atomicNum = results.getInt("atomicNum");
            this.atomicMass = results.getDouble("atomicMass");
            this.elementID = results.getLong("elementID");
            this.compoundID = results.getLong("compoundID");
            this.solute = results.getLong("solute");
            this.dissolvedBy = results.getLong("dissolvedBy");
            this.dissolves = results.getLong("dissolves");

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    public static void createTable() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String dropStatement = "DROP TABLE IF EXISTS SingleTable";
        String createStatement =
                "CREATE TABLE SingleTable (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(25), atomicNum INT, atomicMass DOUBLE," +
                        " elementID BIGINT NOT NULL, compoundID BIGINT NOT NULL, solute BIGINT, dissolvedBy BIGINT, " +
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
            throw new DataException(e.getMessage());
        }
    }

    public static SingleTableGateway createChemical(String name) throws DataException {
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
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createAcid(String name, long solute, long dissolves) throws DataException {
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
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createCompound(String name) throws DataException {
        String query = new String("INSERT INTO SingleTable (name) VALUES (?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createBase(String name, long solute) throws DataException {
        String query = new String("INSERT INTO SingleTable (name, solute) VALUES (?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setLong(2, solute);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createElement(String name, long atomicNum, double atomicMass) throws DataException {
        String query = new String("INSERT INTO SingleTable (name, atomicNum, atomicMass) VALUES (?, ?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setLong(2, atomicNum);
            stmt.setDouble(3, atomicMass);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }

        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public static SingleTableGateway createMetal(String name, int atomicNum, double atomicMass, long dissolvedBy) throws DataException {
        String query = new String("INSERT INTO SingleTable (name, atomicNum, atomicMass, dissolvedBy) VALUES (?, ?, ?, ?)");
        Connection conn = DatabaseConnection.getInstance().getConnection();
        long id = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setInt(2, atomicNum);
            stmt.setDouble(3, atomicMass);
            stmt.setLong(4, dissolvedBy);
            if (stmt.executeUpdate() > 0) {
                id = getIDFromDatabase(stmt);
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }

        return new SingleTableGateway(id);
    }

    public void persist() throws DataException {
        String query = "UPDATE SingleTable SET name = ?, " +
                "atomicNum = ?, " +
                "atomicMass = ?, " +
                "solute = ?, " +
                "compoundID = ?, " +
                "elementID = ?, " +
                "dissolvedBy = ?, " +
                "dissolves = ? " +
                "WHERE id = " + id;

        try (PreparedStatement stmt = this.connection.prepareStatement(query)){
            stmt.setString(1, this.name);
            stmt.setLong(2, this.atomicNum);
            stmt.setDouble(3, this.atomicMass);
            stmt.setLong(4, this.solute);
            stmt.setLong(5, this.compoundID);
            stmt.setLong(6, this.elementID);
            stmt.setLong(7, this.dissolvedBy);
            stmt.setLong(8, this.dissolves);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * Tries to return the ID value for the new row being added
     *
     * @param stmt the prepared statement we're pulling the ID from
     * @return the ID of the row being added
     */
    private static long getIDFromDatabase(PreparedStatement stmt) throws DataException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
        return 0;
    }

    public void delete() throws DataException {
        String query = "DELETE FROM SingleTable WHERE id = " + id;
        try (PreparedStatement stmt = this.connection.prepareStatement(query)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAtomicNum() {
        return atomicNum;
    }

    public void setAtomicNum(long atomicNum) {
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

    public long getCompoundID() {
        return compoundID;
    }

    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
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

    /** TDG **/
    public static ArrayList<ElementDTO> getAllBetween(int startAtomicNum, int endAtomicNum) throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM SingleTable WHERE atomicNum BETWEEN " + startAtomicNum + " AND " + endAtomicNum;
        return getElementDTOs(conn, query);
    }

    public static ArrayList<ElementDTO> getAllElements() throws DataException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM SingleTable WHERE atomicNum IS NOT NULL AND atomicMass IS NOT NULL";
        return getElementDTOs(conn, query);
    }

    private static ArrayList<ElementDTO> getElementDTOs(Connection conn, String query) throws DataException {
        ArrayList<ElementDTO> elementsList = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                ElementDTO element = createElementRecord(results);
                elementsList.add(element);
            }
            return elementsList;
        } catch (SQLException e)
        {
            throw new DataException("Could not fetch all elements for the Class Table!", e);
        }
    }

    /**
     * creates a new element DTO using a queries results
     * @param results the results given back from the query
     * @return the element DTO
     */
    private static ElementDTO createElementRecord(ResultSet results) throws DataException {
        try {
            long id = results.getLong("id");
            String name = results.getString("name");
            long atomicNum = results.getLong("atomicNum");
            double atomicMass = results.getDouble("atomicMass");

            return new ElementDTO(id, name, atomicNum, atomicMass);
        } catch (SQLException e)
        {
            throw new DataException("Could not create the Element DTO", e);
        }
    }
}
