package model.mapper;

import datasource.DataException;
import datasource.ElementGateway;
import gatewayDTOs.ElementDTO;
import model.Element;
import model.ElementMapperInterface;

import java.util.ArrayList;

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
        try {
            ElementDTO elementDTO = ElementGateway.findByName(name);
            long id = elementDTO.getId();
            long atomicNumber = elementDTO.getAtomicNum();
            double atomicMass = elementDTO.getAtomicMass();

            element = new Element(id, name, atomicNumber, atomicMass);

        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException("Element: " + name + " could not mapped", e);
        }
    }

    /**
     * Tells the element gateway to persist data to the database for a given row
     * @param id the id of the row
     * @throws DataException the general data exception
     */
    public static void persist(long id, String name, long atomicNumber, double atomicMass) throws DataException, ElementNotFoundException {
        try {
            ElementGateway gateway = new ElementGateway(id);
            gateway.setName(name);
            gateway.setAtomicNum(atomicNumber);
            gateway.setAtomicMass(atomicMass);
            gateway.update();

        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException("Could not persist element data.", e);
        }
    }

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
     *
     * @param startAtomicNum
     * @param endAtomicNum
     * @return
     * @throws ElementNotFoundException
     */
    public static Element[] getElementsBetween(int startAtomicNum, int endAtomicNum) throws ElementNotFoundException {
        ArrayList<ElementDTO> elementDTOs = ElementGateway.getAllBetween(startAtomicNum, endAtomicNum);
        return getElements(elementDTOs);
    }

    /**
     *
     * @return
     */
    public static Element[] getAllElements() {
        ArrayList<ElementDTO> elementDTOs = ElementGateway.findAll();
        return getElements(elementDTOs);
    }

    /**
     *
     * @param elementDTOs
     * @return
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
     * @return Element mapper object
     */
    @Override
    public Element getMyElement() {
        return element;
    }
}

