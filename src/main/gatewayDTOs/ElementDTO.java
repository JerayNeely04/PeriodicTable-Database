package gatewayDTOs;

/**
 * Element DTO for the Element gateway
 */
public class ElementDTO {
    private long atomicNumber;
    private double atomicMass;
    private long id;

    /**
     * Constructor for the Element DTO
     * @param atomicNumber is the primary key
     * @param atomicMass
     */
    public ElementDTO(long id, long atomicNumber, double atomicMass) {
        this.id = id;
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
     * Getter for thr atomic mass
     * @return the atomic mass of the element
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * Getter for the id
     * @return the element rows id
     */
    public long getId()
    {
        return id;
    }

}
