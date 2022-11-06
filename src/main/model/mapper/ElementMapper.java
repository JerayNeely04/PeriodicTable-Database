package model.mapper;

import GatewayDTO.ElementDTO;
import GatewayDTO.madeOfDTO;
import datasource.DataException;
import datasource.MadeOfTableGateway;
import datasource.SingleTableGateway;
import model.Element;
import model.ElementMapperInterface;

import java.util.ArrayList;
import java.util.List;

public class ElementMapper implements ElementMapperInterface {

    private Element element;

    /**
     * Create a new element in the database, and store the resulting model object
     * into my instance variable
     * @param name the name of the element
     * @param atomicNumber the elements atomic number
     * @param atomicMass the elements atomic mass
     * @throws DataException exception thrown if the element could not be mapped
     */
    public ElementMapper(String name, long atomicNumber, double atomicMass) throws ElementNotFoundException {
        try {
            SingleTableGateway elementGateway = SingleTableGateway.createElement(name, atomicNumber, atomicMass);
            long id = elementGateway.getId();
            element = new Element(id, name, atomicNumber, atomicMass);

        } catch (DataException e) {
            throw new ElementNotFoundException("Element: " + name + " could not mapped", e);
        }
    }

    /**
     * Constructor for objects that exist in the db
     * @param name the name of the element
     * @throws DataException exception thrown if the element could not be mapped
     */
    public ElementMapper(String name) throws ElementNotFoundException {
        try {
            SingleTableGateway elementGateway = new SingleTableGateway(name);

            long id = elementGateway.getId();
            long atomicNumber = elementGateway.getAtomicNum();
            double atomicMass = elementGateway.getAtomicMass();

            element = new Element(id, name, atomicNumber, atomicMass);
        } catch (DataException e) {
            throw new ElementNotFoundException("Could not map element", e);
        }
    }

    /**
     * Tells the element gateway to persist data to the database for a given row
     * @param id the id of the row
     * @throws DataException the general data exception
     */
    public static void persist(long id, String name, long atomicNumber, double atomicMass) throws DataException {
        SingleTableGateway gateway = new SingleTableGateway(id);
        gateway.setName(name);
        gateway.setAtomicNum(atomicNumber);
        gateway.setAtomicMass(atomicMass);
        gateway.persist();
    }

    /**
     * Uses the element gateway to delete a row
     * @param name the name of the element to delete
     * @throws DataException
     */
    public static void delete(String name) throws DataException {
        SingleTableGateway gateway = new SingleTableGateway(name);
        gateway.delete();
    }

    /**
     * Uses the element gateway to return all elements between two atomic numbers
     * @param startAtomicNum the starting atomic number
     * @param endAtomicNum the ending atomic number
     * @return the list of element objects between the two atomic numbers
     * @throws ElementNotFoundException
     */
    public static Element[] getElementsBetween(int startAtomicNum, int endAtomicNum) throws DataException {
        ArrayList<ElementDTO> elementDTOs = SingleTableGateway.getAllBetween(startAtomicNum, endAtomicNum);
        return getElements(elementDTOs);
    }

    /**
     * @return all elements in the database using the element gateway
     */
    public static Element[] getAllElements() throws DataException {
        ArrayList<ElementDTO> elementDTOs = SingleTableGateway.getAllElements();
        return getElements(elementDTOs);
    }

    /**
     * Maps a list of element objects using a list of element DTOs
     * @param elementDTOs the list of DTOs to map
     * @return the list of element objects
     */
    private static Element[] getElements(ArrayList<ElementDTO> elementDTOs) {
        Element[] elements = new Element[elementDTOs.size()];

        for(int i = 0; i < elementDTOs.size(); i++) {
            ElementDTO currentDTO = elementDTOs.get(i);
            long id = currentDTO.getId();
            String name = currentDTO.getName();
            double atomicMass = currentDTO.getAtomicMass();
            long atomicNumber = currentDTO.getAtomicNum();

            elements[i] = new Element(id, name, atomicNumber, atomicMass);
        }

        return elements;
    }

    /**
     * @param elementID the id of the element to check against
     * @return the list of all compounds containing an element
     * @throws DataException
     * @throws CompoundNotFoundException
     */
    public static List<String> getCompoundsContaining(long elementID) throws DataException, CompoundNotFoundException {
        ArrayList<madeOfDTO> compounds = MadeOfTableGateway.findByElementID(elementID);
        ArrayList<String> namesOfCompounds = new ArrayList<String>();

        for (madeOfDTO compound: compounds) {
            long compoundID = compound.getCompoundID();
            SingleTableGateway gateway = new SingleTableGateway(compoundID);
            String compoundName = gateway.getName();

            if(!namesOfCompounds.contains(compoundName)) {
                namesOfCompounds.add(gateway.getName());
            }
        }
        return namesOfCompounds;
    }

    /**
     * @return Element mapper object
     */
    @Override
    public Element getMyElement() {
        return element;
    }
}

