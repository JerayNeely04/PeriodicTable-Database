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

    /**
     * Setter for the ID
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for the name of the chemical
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
