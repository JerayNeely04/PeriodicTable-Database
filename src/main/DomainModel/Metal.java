package DomainModel;

public class Metal {
    private long id;
    private long dissolvedBy;
    /**
     * Metal Constructor
     * @param dissolvedBy
     */
    public Metal(long id, long dissolvedBy)
    {
        this.id = id;
        this.dissolvedBy = dissolvedBy;
    }

    /**
     * getter for metal ID
     * @return Metal ID
     */
    public long getId() { return id; }

    /**
     * getter for DissolvedBy
     * @return
     */
    public long getDissolvedBy()
    {
        return dissolvedBy;
    }
}
