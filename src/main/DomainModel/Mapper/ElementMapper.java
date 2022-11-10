package DomainModel.Mapper;

import DomainModel.Element;
import DomainModel.ElementMapperInterface;
import datasource.DataException;
import datasource.ChemicalTableGateway;
import datasource.ElementTableGateway;
import datasource.madeOfTable;
import gatewayDTOs.ChemicalDTO;
import gatewayDTOs.ElementDTO;
import gatewayDTOs.madeOfDTO;

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
        ChemicalDTO chemicalDTO;
        long id;

        try {
            chemicalDTO = ChemicalTableGateway.findByName(name);
            id = chemicalDTO.getId();
            ElementTableGateway.createElement(id, atomicNumber, atomicMass);

        } catch (ChemicalNotFoundException ex) {
            try {
                ChemicalTableGateway.createChemical(name);
                chemicalDTO = ChemicalTableGateway.findByName(name);
                id = chemicalDTO.getId();
                ElementTableGateway.createElement(id, atomicNumber, atomicMass);

            } catch (DataException | ChemicalNotFoundException e) {
                throw new ElementNotFoundException("Element: " + name + " could not mapped", e);
            }
        }

        element = new Element(id, name, atomicNumber, atomicMass);
    }

    /**
     * Constructor for objects that exist in the db
     * @param name the name of the element
     * @throws ElementNotFoundException exception thrown if the element could not be mapped
     */
    public ElementMapper (String name) throws ElementNotFoundException {
        try {
            long id = ChemicalTableGateway.findByName(name).getId();
            ElementDTO elementDTO = ElementTableGateway.findById(id);

            element = new Element(id, name, elementDTO.getAtomicNumber(), elementDTO.getAtomicMass());
        } catch (ElementNotFoundException | ChemicalNotFoundException e) {
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
            // Update the chemical row
            ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(id);
            chemicalGateway.setName(name);
            chemicalGateway.persist();

            // Update the element row
            ElementTableGateway gateway = new ElementTableGateway(id);
            gateway.setAtomicNumber(atomicNumber);
            gateway.setAtomicMass(atomicMass);
            gateway.persist();

        } catch (ChemicalNotFoundException e) {
            try {
                // If the chemical wasn't found this is using the concrete pattern
                // implementation:
                ElementTableGateway gateway = new ElementTableGateway(id);

                // cannot have setters in the DTO, need set methods for the RDG
                gateway.setAtomicNumber(atomicNumber);
                gateway.setAtomicMass(atomicMass);
                // set name

                gateway.persist();
            } catch (ElementNotFoundException ex) {
                // try to update single table
                throw new ElementNotFoundException("Could not persist element data.", e);
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException("Could not persist element data.", e);
        }
    }

    /**
     *
     * @param name
     * @throws ChemicalNotFoundException
     * @throws DataException
     */
    public static void delete(String name) throws ChemicalNotFoundException, DataException {
        long id = ChemicalTableGateway.findByName(name).getId();
        ChemicalTableGateway gateway = new ChemicalTableGateway(id);
        gateway.delete();
    }

    /**
     *
     * @param startAtomicNum
     * @param endAtomicNum
     * @return
     * @throws DataException
     * @throws ElementNotFoundException
     */
    public static Element[] getElementsBetween(int startAtomicNum, int endAtomicNum) throws DataException, ElementNotFoundException {
        ArrayList<ElementDTO> elementDTOs = ElementTableGateway.findAllBetween(startAtomicNum, endAtomicNum);
        return getElements(elementDTOs);
    }

    /**
     * @param elementID the id of the element to check against
     * @return the list of all compounds containing an element
     * @throws DataException
     * @throws CompoundNotFoundException
     */
    public static List<String> getCompoundsContaining(long elementID) throws DataException, ChemicalNotFoundException {
        ArrayList<madeOfDTO> compounds = madeOfTable.findByElementID(elementID);
        ArrayList<String> namesOfCompounds = new ArrayList<>();

        for (madeOfDTO compound: compounds) {
            long compoundID = compound.getCompoundID();
            ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(compoundID);
            String compoundName = chemicalGateway.getName();

            if(!namesOfCompounds.contains(compoundName)) {
                namesOfCompounds.add(chemicalGateway.getName());
            }
        }
        return namesOfCompounds;
    }

    /**
     *
     * @return
     * @throws DataException
     * @throws ElementNotFoundException
     */
    public static Element[] getAllElements() throws DataException, ElementNotFoundException {
        ArrayList<ElementDTO> elementDTOs = ElementTableGateway.findAll();
        return getElements(elementDTOs);
    }

    /**
     *
     * @param elementDTOs
     * @return
     * @throws ElementNotFoundException
     */
    private static Element[] getElements(ArrayList<ElementDTO> elementDTOs) throws ElementNotFoundException {
        Element[] elements = new Element[elementDTOs.size()];

        for(int i = 0; i < elementDTOs.size(); i++) {
            try {
                ElementDTO currentDTO = elementDTOs.get(i);
                long id = currentDTO.getId();
                double atomicMass = currentDTO.getAtomicMass();
                long atomicNumber = currentDTO.getAtomicNumber();

                ChemicalTableGateway gateway = ChemicalTableGateway.findById(id);
                String name = gateway.getName();

                elements[i] = new Element(id, name, atomicNumber, atomicMass);
            } catch (ChemicalNotFoundException e) {
                throw new ElementNotFoundException("Element could not be mapped", e);
            }
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
