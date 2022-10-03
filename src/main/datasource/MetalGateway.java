package datasource;
import java.util.ArrayList;

/**
 * singleton for the element table
 */
public class MetalGateway {
    private static MetalGateway singleton;

    /**
     * @return the only instance of the element TDG
     */
    public static MetalGateway getInstance() {
        if (singleton == null) {
            singleton = new MetalGateway();
        }
        return singleton;
    }

    /**
     * private constructor to create the singleton
     */
    private MetalGateway() {}

    /**
     * every entry in the element table
     * @return DTO containing the element data
     */
    public ArrayList<MetalGateway> findAll()
    {
        return null;
    }

    /**
     * @param atomicNumber the atomic number of the element
     * @return the element with the matching atomic number
     */
    public MetalGateway findByAtomicNumber(long atomicNumber)
    {
        return null;
    }

    /**
     * @param name of the element
     * @return the element with the matching name
     */
    public MetalGateway findByName(String name)
    {
        return null;
    }

    /**
     * creates a new row in the element table
     */
    public void createRow()
    {
        KeyRowDataGateway.generateId();
    }
}
