package model.mapper;

import datasource.CompoundGateway;
import datasource.DataException;
import datasource.ElementGateway;
import datasource.MadeOfGateway;
import gatewayDTOs.ElementDTO;
import gatewayDTOs.MadeOfDTO;
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
     * @throws ElementNotFoundException exception thrown if the element could not be mapped
     */
    public ElementMapper (String name, long atomicNumber, double atomicMass) throws ElementNotFoundException {
        try {
            ElementGateway gateway = new ElementGateway(name, atomicNumber, atomicMass);
            long id = gateway.getId();
            element = new Element(id, name, atomicNumber, atomicMass);

        } catch (DataException e) {
            throw new ElementNotFoundException("Element: " + name + " could not mapped", e);
        }
    }

    /**
     * Constructor for objects that exist in the db
     * @param name the name of the element
     * @throws ElementNotFoundException exception thrown if the element could not be mapped
     */
    public ElementMapper (String name) throws ElementNotFoundException {
        ElementDTO elementDTO = ElementGateway.findByName(name);
        long id = elementDTO.getId();
        long atomicNumber = elementDTO.getAtomicNum();
        double atomicMass = elementDTO.getAtomicMass();

        element = new Element(id, name, atomicNumber, atomicMass);
    }

    /**
     * Tells the element gateway to persist data to the database for a given row
     * @param id the id of the row
     * @throws DataException the general data exception
     */
    public static void persist(long id, String name, long atomicNumber, double atomicMass) throws ElementNotFoundException {
        ElementGateway gateway = new ElementGateway(id);
        gateway.setName(name);
        gateway.setAtomicNum(atomicNumber);
        gateway.setAtomicMass(atomicMass);
        gateway.update();
    }

    /**
     * Uses the element gateway to delete a row
     * @param name the name of the element to delete
     * @throws ElementNotFoundException
     */
    public static void delete(String name) throws ElementNotFoundException {
        try {
            ElementDTO elementDTO = ElementGateway.findByName(name);
            long id = elementDTO.getId();

            ElementGateway gateway = new ElementGateway(id);
            gateway.delete();

        } catch(ElementNotFoundException e) {
            throw new ElementNotFoundException("Could not delete element row.", e);
        }
    }

    /**
     * Uses the element gateway to return all elements between two atomic numbers
     * @param startAtomicNum the starting atomic number
     * @param endAtomicNum the ending atomic number
     * @return the list of element objects between the two atomic numbers
     * @throws ElementNotFoundException
     */
    public static Element[] getElementsBetween(int startAtomicNum, int endAtomicNum) throws ElementNotFoundException {
        ArrayList<ElementDTO> elementDTOs = ElementGateway.getAllBetween(startAtomicNum, endAtomicNum);
        return getElements(elementDTOs);
    }

    /**
     * @return all elements in the database using the element gateway
     */
    public static Element[] getAllElements() {
        ArrayList<ElementDTO> elementDTOs = ElementGateway.findAll();
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
        ArrayList<MadeOfDTO> compounds = MadeOfGateway.findByElementID(elementID);
        ArrayList<String> namesOfCompounds = new ArrayList<String>();

        for (MadeOfDTO compound: compounds) {
            long compoundID = compound.getCompoundID();
            CompoundGateway gateway = new CompoundGateway(compoundID);
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

