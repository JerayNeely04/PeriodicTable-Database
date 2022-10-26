package DomainModel.Mapper;

import DomainModel.Chemical;
import DomainModel.ChemicalMapperInterface;
import datasource.ChemicalTableGateway;
import datasource.DataException;
import gatewayDTOs.ChemicalDTO;

public class ChemicalMapper implements ChemicalMapperInterface {
    private Chemical myChemical;

    public ChemicalMapper(long id, String name) throws DataException {
        ChemicalTableGateway.createChemical(name);
        id = ChemicalTableGateway.findByName(name).getId();
        myChemical = new Chemical(id, name);
    }

    public ChemicalMapper(String name) throws DataException {
        ChemicalDTO chemical = ChemicalTableGateway.findByName(name);
        myChemical = new Chemical(chemical.getId(), chemical.getName());
    }

    @Override
    public Chemical getMyChemical() {
        return myChemical;
    }
}
