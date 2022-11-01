package gatewayDTOs;

/**
 * Chemical Dto for the Chemical gateway
 */
public class ChemicalDTO {
    private long id;
    private String name;

    /**
     * Chemical constructor
     * @param id is the primary key for the chemical Table
     * @param name name of the Chemical
     */
    public ChemicalDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * getter for the ID
     * @return chemical ID
     */
    public long getId() {
        return id;
    }

    /**
     * get the name of the chemical
     */
    public String getName() {
        return name;
    }

    //TODO: REMOVE
    public void setName(String newName) { name = newName; }
}
