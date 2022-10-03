package datasource;
import java.util.ArrayList;

/**
 * singleton for the element table
 */
public class MetalTDG {
    private static MetalTDG singleton;

    /**
     * @return the only instance of the element TDG
     */
    public static MetalTDG getInstance() {
        if (singleton == null) {
            singleton = new MetalTDG();
        }
        return singleton;
    }

    /**
     * private constructor to create the singleton
     */
    private MetalTDG() {}

    /**
     * every entry in the element table
     * @return DTO containing the element data
     */
    public ArrayList<MetalTDG> findAll()
    {
        return null;
    }

    /**
     * @param atomicNumber the atomic number of the element
     * @return the element with the matching atomic number
     */
    public MetalTDG findByAtomicNumber(long atomicNumber)
    {
        return null;
    }

    /**
     * @param name of the element
     * @return the element with the matching name
     */
    public MetalTDG findByName(String name)
    {
        return null;
    }

    /**
     * creates a new row in the element table
     */
    public void createRow()
    {
        KeyRDG.generateId();
    }
}
