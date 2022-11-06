package model;

import datasource.DataException;
import datasource.MadeOfGateway;
import model.mapper.CompoundMapper;
import model.mapper.CompoundNotFoundException;
import model.mapper.ElementMapper;
import model.mapper.ElementNotFoundException;

import java.util.List;

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

    public void delete() {

    }

    public void persist() throws DataException, ElementNotFoundException {
        ElementMapper.persist(id, name, atomicNumber, atomicMass);
    }

    /**
     * Getter for the period
     * @return the period the element is in
     */
    public int getPeriod() {
        int period = 0;

        if (atomicNumber <= 2) {
            period = 1;
        } else if (atomicNumber <= 10) {
            period = 2;
        } else if (atomicNumber <= 18) {
            period = 3;
        } else if (atomicNumber <= 36) {
            period = 4;
        } else if (atomicNumber <= 54) {
            period = 5;
        } else if (atomicNumber <= 86) {
            period = 6;
        } else {
            period = 7;
        }

        return period;
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

    public List<String> getCompoundsContaining() throws DataException, CompoundNotFoundException {
        return ElementMapper.getCompoundsContaining(id);
    }
}


