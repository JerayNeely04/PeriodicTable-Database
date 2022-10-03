package gatewayDTOs;

public class AcidDTO {
    private Long id;
    private String name;
  private long Solute;

    /**
     * 
     * @param id
     * @param name
     * @param solute
     */
    public AcidDTO(long id, String name,long solute ){

        this.id = id;
        this.name = name;
        this.Solute = solute;
    }

    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param solute
     */
    public void setSolute(long solute) {
        Solute = solute;
    }

    /**
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @return the name;
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return the solute
     */
    public long getSolute() {
        return Solute;
    }
}
