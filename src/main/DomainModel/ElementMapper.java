package DomainModel;

import datasource.DataException;
import datasource.ChemicalTableGateway;
import datasource.ElementTableGateway;
import gatewayDTOs.Chemical;

public class ElementMapper implements ElementMapperInterface {

    private Element myElement;

    /**
     * Create a new element in the database, and store the resulting model object
     * into my instance variable
     */
    public ElementMapper (String name, long atomicNumber, double atomicMass) throws DataException {
        ChemicalTableGateway.createChemical(name);
        long id = ChemicalTableGateway.findByName(name).getId();
        ElementTableGateway.createElement(id, atomicNumber, atomicMass);
    }

    /**
     * Constructor for objects that exist in the db
     * @param name
     */
    public ElementMapper (String name) {
        myElement = ElementTableGateway.findByName(name);
    }

    @Override
    public Element getMyElement() {
        return myElement;
    }
}
