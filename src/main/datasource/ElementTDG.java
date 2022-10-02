package datasource;

import gatewayDTOs.ElementDTO;

import java.util.ArrayList;

/**
 * singleton for the element table
 */
public class ElementTDG {
    private static ElementTDG singleton;

    /**
     * @return the only instance of the element TDG
     */
    public static ElementTDG getInstance() {
        if (singleton == null) {
            singleton = new ElementTDG();
        }
        return singleton;
    }

    /**
     * private constructor to create the singleton
     */
    private ElementTDG() {}

    /**
     * every entry in the element table
     * @return DTO containing the element data
     */
    public ArrayList<ElementDTO> findAll()
    {
        return null;
    }

    /**
     * @param atomicNumber the atomic number of the element
     * @return the element with the matching atomic number
     */
    public ElementDTO findByAtomicNumber(long atomicNumber)
    {
        return null;
    }

    /**
     * @param name of the element
     * @return the element with the matching name
     */
    public ElementDTO findByName(String name)
    {
        return null;
    }

    /**
     * creates a new row in the element table
     */
    public void createRow()
    {

    }
}
