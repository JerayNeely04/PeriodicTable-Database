package DomainModel;

public class Element {
    private String name;
    private long atomicNumber;
    private double atomicMass;

    /**
     * Constructor for the Element
     * @param atomicNumber is the primary key
     * @param atomicMass
     */
    public Element(String name, long atomicNumber, double atomicMass) {
        this.name = name;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
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
}
