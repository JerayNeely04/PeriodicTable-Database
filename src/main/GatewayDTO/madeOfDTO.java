package GatewayDTO;

public class madeOfDTO {
    private long compoundID;
    private long elementID;

    public madeOfDTO(long compoundID, long elementID) {
        this.compoundID = compoundID;
        this.elementID = elementID;
    }

    public long getCompoundID() {
        return compoundID;
    }

    public void setCompoundID(long compoundID) {
        this.compoundID = compoundID;
    }

    public long getElementID() {
        return elementID;
    }

    public void setElementID(long elementID) {
        this.elementID = elementID;
    }
}
