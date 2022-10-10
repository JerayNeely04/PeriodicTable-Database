package gatewayDTOs;

public class MadeOfDTO {
    private long compoundID;
    private long elementID;

    /**
     * creates a new made of DTO
     *
     * @param compoundID the compound id
     * @param elementID  the element
     */
    public MadeOfDTO(long compoundID, long elementID) {
        this.compoundID = compoundID;
        this.elementID = elementID;
    }

    /**
     * @return the compound id
     */
    public long getCompoundID() {
        return compoundID;
    }

    /**
     * sets a new compound id
     *
     * @param compoundID the compound id to set
     */
    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
    }

    /**
     * @return the element id
     */
    public long getElementID() {
        return elementID;
    }

    /**
     * sets a new element id
     *
     * @param elementID the element id to set
     */
    public void setElementID(long elementID) {
        this.elementID = elementID;
    }
}
