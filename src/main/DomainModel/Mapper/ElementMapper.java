package DomainModel.Mapper;

import DomainModel.Element;
import DomainModel.ElementMapperInterface;
import datasource.DataException;
import datasource.ChemicalTableGateway;
import datasource.ElementTableGateway;
import gatewayDTOs.ElementDTO;

public class ElementMapper implements ElementMapperInterface {

    private Element myElement;

    /**
     * Create a new element in the database, and store the resulting model object
     * into my instance variable
     */
    public ElementMapper (String name, long atomicNumber, double atomicMass) throws DataException {
        long id = ChemicalTableGateway.findByName(name).getId();
        ElementTableGateway.createElement(atomicNumber, atomicMass, id);

        myElement = new Element(name, atomicNumber, atomicMass);
    }

    /**
     * Constructor for objects that exist in the db
     * @param name
     */
    public ElementMapper (String name) throws DataException {
        long id = ChemicalTableGateway.findByName(name).getId();
        ElementDTO element = ElementTableGateway.findById(id);

        myElement = new Element(name, element.getAtomicNumber(), element.getAtomicMass());
    }

    /**
     * @return Element mapper object
     */
    @Override
    public Element getMyElement() {
        return myElement;
    }
}
