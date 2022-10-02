package gatewayDTOs;

public class ElementDTO {
    private long id;
    private String name;
    private long atomicNumber;
    private long atomicMass;

    /**
     * creates a new object for an element
     * @param id primary key
     * @param name the name of the element
     * @param atomicNumber the atomic number
     * @param atomicMass the atomic mass value
     */
    public ElementDTO(long id, String name, long atomicNumber, long atomicMass)
    {
        this.id = id;
        this.name = name;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name of the element
     */
    public String getName() {
        return name;
    }

    /**
     * changes the name of the element
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the elements atomic number
     */
    public long getAtomicNumber() {
        return atomicNumber;
    }

    /**
     * changes the elements atomic number
     * @param atomicNumber new atomic number
     */
    public void setAtomicNumber(long atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    /**
     * @return the elements atomic mass
     */
    public long getAtomicMass() {
        return atomicMass;
    }

    /**
     * changes the elements atomic mass
     * @param atomicMass the new atomic mass
     */
    public void setAtomicMass(long atomicMass) {
        this.atomicMass = atomicMass;
    }
}
