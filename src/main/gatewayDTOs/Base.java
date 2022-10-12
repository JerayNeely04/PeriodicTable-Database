package gatewayDTOs;

/**
 * Base Dto for the Base Gateway
 */
public class Base {
    private long solute;

    /**
     * Base constructor
     * @param solute
     */
    public Base(long solute) {
        this.solute = solute;
    }

    /**
     * set the solute for base
     * @param solute
     */
    public void setSolute(long solute) { this.solute = solute; }

    /**
     * get the solute for base
     * @return solute
     */
    public long getSolute() { return solute; }
}
