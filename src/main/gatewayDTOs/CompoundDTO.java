package gatewayDTOs;

public class CompoundDTO {
    private long id;
    private String name;

    /**
     * creates a new object for an element
     * @param id primary key
     * @param name the name of the element
     */
    public CompoundDTO(long id, String name)
    {
        this.id = id;
        this.name = name;
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

}


