package gatewayDTOs;

public class ElementDTO {
    private long id;
    private String name;
    private long atomicNum;
    private double atomicMass;

    /**
     * creates a new object for an element
     * @param id primary key
     * @param name the name of the element
     * @param atomicNum the atomic number
     * @param atomicMass the atomic mass value
     */
    public ElementDTO(long id, String name, long atomicNum, double atomicMass)
    {
        this.id = id;
        this.name = name;
        this.atomicNum = atomicNum;
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
    public long getAtomicNum() {
        return atomicNum;
    }

    /**
     * changes the elements atomic number
     * @param atomicNum new atomic number
     */
    public void setAtomicNum(long atomicNum) {
        this.atomicNum = atomicNum;
    }

    /**
     * @return the elements atomic mass
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * changes the elements atomic mass
     * @param atomicMass the new atomic mass
     */
    public void setAtomicMass(long atomicMass) {
        this.atomicMass = atomicMass;
    }

    public static class MetalDTO {
    }
}
