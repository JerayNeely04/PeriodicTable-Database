package gatewayDTOs;

public class madeOfDTO {
    private long compoundID;
    private long elementID;

    /**
     * Create a DTO named madeOf
     *
     * @param compoundID   compoundID in DTO
     * @param elementID    elementID in DTO
     */
    public madeOfDTO(long compoundID, long elementID) {
        this.compoundID = compoundID;
        this.elementID = elementID;
    }

    /**
     * Gets compoundID from DTO
     *
     * @return the compoundID
     */
    public long getCompoundID() {
        return compoundID;
    }

    /**
     * Set compoundID in DTO
     *
     * @param compoundID  The new compoundID for DTO
     */
    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
    }

    /**
     * Get elementID from DTO
     *
     * @return the elementID belonging to the DTO
     */
    public long getElementID() {
        return elementID;
    }

    /**
     * Set the elementID in the DTO
     *
     * @param elementID    The new elementID for the DTO
     */
    public void setElementID(long elementID) {
        this.elementID = elementID;
    }
}