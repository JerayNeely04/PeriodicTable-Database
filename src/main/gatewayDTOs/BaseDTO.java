package gatewayDTOs;

public class BaseDTO {
    long id;
    String name;
    long solute;

    /**
     * Creates a new base DTO
     * @param id the id
     * @param name the name
     * @param solute the foreign key for the solute
     */
    public BaseDTO(long id, String name, long solute)
    {
        this.id = id;
        this.name = name;
        this.solute = solute;
    }

    /**
     * @return the id of the row
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name of the base
     */
    public String getName() {
        return name;
    }

    /**
     * changes the name of the base
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the solute id
     */
    public long getSolute() {
        return solute;
    }

    /**
     * changes the solute
     * @param solute the solute
     */
    public void setSolute(long solute) {
        this.solute = solute;
    }
}
