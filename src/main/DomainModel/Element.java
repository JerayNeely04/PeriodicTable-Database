package DomainModel;

public class Element {
    private long atomicNumber;
    private double atomicMass;

    /**
     * Constructor for the Element
     * @param atomicNumber is the primary key
     * @param atomicMass
     */
    public Element(long atomicNumber, double atomicMass) {
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
    }

    /**
     * Getter for the Atomic number
     * @return atomic number of the element
     */
    public long getAtomicNumber() {
        return atomicNumber;
    }

    /**
     * Setter for the Atomic number
     * @param atomicNumber
     */
    public void setAtomicNumber(long atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    /**
     * Getter for thr atomic mass
     * @return the atomic mass of the element
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * Setter for the atomic mass
     * @param atomicMass
     */
    public void setAtomicMass(double atomicMass) {
        this.atomicMass = atomicMass;
    }
}
