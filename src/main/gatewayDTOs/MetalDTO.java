package gatewayDTOs;

/**
 * Metal Dto for the Metal Gateway
 */
public class MetalDTO {
    private long dissolvedBy;

    /**
     * Metal Constructor
     * @param dissolvedBy
     */
    public MetalDTO(long dissolvedBy)
    {
        this.dissolvedBy = dissolvedBy;
    }

    /**
     * setter for DissolvedBy
     * @param dissolvedBy
     */
    public void setDissolvedBy(long dissolvedBy)
    {
        this.dissolvedBy = dissolvedBy;
    }

    /**
     * getter for DissolvedBy
     * @return
     */
    public long getDissolvedBy()
    {
        return dissolvedBy;
    }
}
