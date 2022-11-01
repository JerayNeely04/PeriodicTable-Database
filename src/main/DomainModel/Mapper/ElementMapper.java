package DomainModel.Mapper;

import DomainModel.Element;
import DomainModel.ElementMapperInterface;
import datasource.DataException;
import datasource.ChemicalTableGateway;
import datasource.ElementTableGateway;
import gatewayDTOs.ElementDTO;

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
            ChemicalTableGateway.createChemical(name);
            long id = ChemicalTableGateway.findByName(name).getId();
            ElementTableGateway.createElement(id, atomicNumber, atomicMass);

            element = new Element(id, name, atomicNumber, atomicMass);

        } catch (DataException | ChemicalNotFoundException e) {
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
            long id = ChemicalTableGateway.findByName(name).getId();
            ElementDTO elementDTO = ElementTableGateway.findById(id);

            element = new Element(id, name, elementDTO.getAtomicNumber(), elementDTO.getAtomicMass());
        } catch (ElementNotFoundException | DataException | ChemicalNotFoundException e) {
            throw new ElementNotFoundException("Element: " + name + " could not mapped", e);
        }
    }

    /**
     * Tells the element gateway to persist data to the database for a given row
     * @param id the id of the row
     * @throws DataException the general data exception
     */
    public static void persist(long id, String name, long atomicNumber, double atomicMass) throws DataException {
        try {
            // Update the chemical row
            ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(id);
            chemicalGateway.chemicalDTO.setName(name);
            chemicalGateway.persist();

            // Update the element row
            ElementTableGateway gateway = new ElementTableGateway(id);
            gateway.elementDTO.setAtomicNumber(atomicNumber);
            gateway.elementDTO.setAtomicMass(atomicMass);
            gateway.persist();

        } catch (ChemicalNotFoundException e) {
            // If the chemical wasn't found this is using the concrete pattern
            // implementation:
            ElementTableGateway gateway = new ElementTableGateway(id);

            // cannot have setters in the DTO, need set methods for the RDG
            gateway.elementDTO.setAtomicNumber(atomicNumber);
            gateway.elementDTO.setAtomicMass(atomicMass);
            gateway.persist();
        }
    }

    /**
     * @return Element mapper object
     */
    @Override
    public Element getMyElement() {
        return element;
    }
}
