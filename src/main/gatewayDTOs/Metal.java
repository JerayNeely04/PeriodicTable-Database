package gatewayDTOs;

public class Metal {
    private long dissolvedBy;
    public Metal(long dissolvedBy)
    {
        this.dissolvedBy = dissolvedBy;
    }
    public void setDissolvedBy(long dissolvedBy)
    {
        this.dissolvedBy = dissolvedBy;
    }
    public long getDissolvedBy()
    {
        return dissolvedBy;
    }
}
