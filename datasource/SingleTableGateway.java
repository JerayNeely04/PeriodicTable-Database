package datasource;

import java.sql.Connection;

public class SingleTableGateway {
    private Long id;
    private String name;
    private int atomicNum;
    private double atomicMass;
    private ListOfElements madeOf;
    private  Chemical solute;
    private Acid dissolvedBy;
    private ListOfChemicals chemicalsDissolvedBy;

    public void create(String name, int atomicNumber, double atomicMass)
    {
        this.id = id;
        this.name = name;
        this.atomicNum = atomicNum;
    }
    public void create(String name, Acid dissolvedBy)
    {
        this.name = name;
        this.dissolvedBy = dissolvedBy;
    }
    public void create(String name, ListOfElements madeOf)
    {
        this.name = name;
        this.madeOf = madeOf;
    }
    public void create(String name, Chemical solute)
    {
        this.name = name;
        this.solute = solute;
    }
    public void create(String name, Chemical solute, ListOfChemicals chemicalsDissolvedBy)
    {
        this.name = name;
        this.solute = solute;
        this.chemicalsDissolvedBy = chemicalsDissolvedBy;
    }

    public ChemicalRDG find(Long id)
    {
        return;
    }

    public void persist()
    {

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
