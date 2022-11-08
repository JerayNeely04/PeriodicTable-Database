package gatewayDTOs;

public class AcidDTO {
    private long solute;

    /**
     * Create new Acid DTO
     *
     * @param solute Value for solute in DTO
     */
    public AcidDTO(long solute) {
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


