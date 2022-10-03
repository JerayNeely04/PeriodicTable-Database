package gatewayDTOs;

public class Acid {
    private long solute;

    // TODO: Acid should only have solute.
    //  There is no id or name

    public Acid(long solute) {
        this.solute = solute;
    }

    public long getSolute() {
        return solute;
    }

    public void setSolute(long solute) {
        this.solute = solute;
    }

}


