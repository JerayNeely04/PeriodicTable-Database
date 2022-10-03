package gatewayDTOs;

public class Element {
    private long atomicNumber;
    private double atomicMass;

    public Element(long atomicNumber, double atomicMass) {
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
    }

    public long getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(long atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public double getAtomicMass() {
        return atomicMass;
    }

    public void setAtomicMass(double atomicMass) {
        this.atomicMass = atomicMass;
    }
}
