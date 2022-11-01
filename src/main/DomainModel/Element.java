package DomainModel;

import DomainModel.Mapper.ElementMapper;
import datasource.DataException;

public class Element {
    private long id;
    private String name;
    private long atomicNumber;
    private double atomicMass;


    /**
     * Constructor for the Element
     * @param id the id of the row
     * @param name the name of the element
     * @param atomicNumber the elements atomic number
     * @param atomicMass the elements atomic mass
     */
    public Element(long id, String name, long atomicNumber, double atomicMass) {
        this.id = id;
        this.name = name;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
    }

    public void persist() throws DataException {
        ElementMapper.persist(id, name, atomicNumber, atomicMass);
    }

    /**
     * Getter for the period
     * @return the period the element is in
     */
    public int getPeriod() {
        // map the atomic number to the period
        return 0;
    }

    /**
     * Getter for the name
     * @return the name of the element
     */
    public String getName() { return name; }

    /**
     * Getter for the Atomic number
     * @return atomic number of the element
     */
    public long getAtomicNumber() {
        return atomicNumber;
    }

    /**
     * Getter for thr atomic mass
     * @return the atomic mass of the element
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * Getter for the id
     * @return the elements row id
     */
    public long getId() { return id; }

    /**
     * Setter for the element name
     * @param newName the new name of the element
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Setter for the atomic number
     * @param newAtomicNumber sets a new atomic number
     */
    public void setAtomicNumber(int newAtomicNumber) {
        atomicNumber = newAtomicNumber;
    }

    /**
     * Setter for the atomic mass
     * @param newAtomicMass the new atomic mass
     */
    public void setAtomicMass(double newAtomicMass) {
        atomicMass = newAtomicMass;
    }
}
