package gatewayDTOs;

public class Acid {
    private long solute;

    /**
     * Create new Acid DTO
     *
     * @param solute Value for solute in DTO
     */
    public Acid(long solute) {
        this.solute = solute;
    }

    /**
     * Get solute from DTO
     *
     * @return the solute belonging to the DTO
     */
    public long getSolute() {
        return solute;
    }

    /**
     * Set solute in DTO
     *
     * @param solute new solute value for DTO
     */
    public void setSolute(long solute) {
        this.solute = solute;
    }

}


