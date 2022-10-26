package DomainModel;

/**
 * Chemical Dto for the Chemical gateway
 */
public class Chemical {
    private long id;
    private String name;

    /**
     * Chemical constructor
     * @param id is the primary key for the chemical Table
     * @param name name of the Chemical
     */
    public Chemical(long id, String name) {
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
}
