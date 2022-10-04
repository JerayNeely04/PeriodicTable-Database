package gatewayDTOs;

public class Base {
    private long solute;
    public Base(long solute) {
        this.solute = solute;
    }
    public void setSolute(long solute) { this.solute = solute; }
    public long getSolute() { return solute; }
}
