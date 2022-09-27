package datasource;

import chemicaltypes.Acid;
import chemicaltypes.Chemical;
import chemicaltypes.ListOfChemicals;
import chemicaltypes.ListOfElements;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleTableGateway {
    private Long id;
    private String name;
    private int atomicNum;
    private double atomicMass;
    private ListOfElements madeOf;
    private Chemical solute;
    private Acid dissolvedBy;
    private ListOfChemicals chemicalsDissolvedBy;
    private DatabaseConnection connection;

    public SingleTableGateway() throws SQLException {
        this.connection = new DatabaseConnection();

        String sql = "CREATE TABLE SingleTable ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(20),"
                + "atomicNum INT,"
                + "atomicMass DOUBLE"
                + "madeOf VARCHAR(20)"
                + "solute VARCHAR(20)"
                + "dissolvedBy VARCHAR(20)"
                + "chemicalsDissolvedBy VARCHAR(20))";
        PreparedStatement ps = this.connection.getConnection().prepareStatement(sql);
        ps.execute();
    }

    public void create(String name, int atomicNum, double atomicMass) throws SQLException {
        this.name = name;
        this.atomicNum = atomicNum;
        this.atomicMass = atomicMass;

        String query = new String("INSERT INTO SingleTable (name, atomicNum, atomicMass) VALUES (?, ?, ?)");

        try
        {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, atomicNum);
            stmt.setDouble(3, atomicMass);
            stmt.executeUpdate();

        } catch (SQLException e)
        {
            System.out.println("Create table failed");
        }
    }
    public void create(String name, Acid dissolvedBy) throws SQLException {
        this.name = name;
        this.dissolvedBy = dissolvedBy;

        String query = new String("INSERT INTO SingleTable (name, dissolvedBy) VALUES (name, dissolvedBy)");
        PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
        stmt.executeUpdate();
    }
    public void create(String name, ListOfElements madeOf) throws SQLException {
        this.name = name;
        this.madeOf = madeOf;

        String query = new String("INSERT INTO SingleTable (name, madeOf) VALUES (name, madeOf)");
        PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
        stmt.executeUpdate();
    }
    public void create(String name, Chemical solute) throws SQLException {
        this.name = name;
        this.solute = solute;

        String query = new String("INSERT INTO SingleTable (name, solute) VALUES (name, solute)");
        PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
        stmt.executeUpdate();
    }
    public void create(String name, Chemical solute, ListOfChemicals chemicalsDissolvedBy) throws SQLException {
        this.name = name;
        this.solute = solute;
        this.chemicalsDissolvedBy = chemicalsDissolvedBy;

        String query = new String("INSERT INTO SingleTable (name, solute, chemicalDissolvedBy) VALUES (name, solute, chemicalDissolvedBy)");
        PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
        stmt.executeUpdate();
    }

    public void find(Long id) throws SQLException {
        String query = new String("SELECT * FROM SingleTable WHERE id =" + id);
        PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
        ResultSet results = stmt.executeQuery();
        results.next();

        this.name = results.getString("name");
        this.atomicNum = results.getInt("atomicNum");
        this.atomicMass = results.getDouble("atomicMass");
//        this.madeOf = results.get
//        this.solute;
//        this.dissolvedBy;
//        this.chemicalsDissolvedBy;
    }

    public void persist()
    {
        String query = new String("UPDATE SingleTable SET name = ?, atomicNum = ?, atomicMass = ?, madeOf = ?, solute = ?, dissolvedBy = ?, chemicalDissolvedBy = ?");

        try
        {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.atomicNum);
            stmt.setDouble(3, this.atomicMass);

            stmt.executeUpdate();
        } catch (SQLException e)
        {

        }
    }

    public void delete()
    {

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

    public ListOfElements getMadeOf() {
        return madeOf;
    }

    public void setMadeOf(ListOfElements madeOf) {
        this.madeOf = madeOf;
    }

    public Chemical getSolute() {
        return solute;
    }

    public void setSolute(Chemical solute) {
        this.solute = solute;
    }

    public Acid getDissolvedBy() {
        return dissolvedBy;
    }

    public void setDissolvedBy(Acid dissolvedBy) {
        this.dissolvedBy = dissolvedBy;
    }

    public ListOfChemicals getChemicalsDissolvedBy() {
        return chemicalsDissolvedBy;
    }

    public void setChemicalsDissolvedBy(ListOfChemicals chemicalsDissolvedBy) {
        this.chemicalsDissolvedBy = chemicalsDissolvedBy;
    }
}
