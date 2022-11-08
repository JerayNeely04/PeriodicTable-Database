package DomainModel.Mapper;

import DomainModel.Metal;
import DomainModel.MetalMapperInterface;
import datasource.DataException;
import datasource.MetalTableGateway;
import gatewayDTOs.MetalDTO;

public class MetalMapper implements MetalMapperInterface {

    private Metal myMetal;
//
//    public MetalMapper(long id, long dissolvedBy) throws DataException {
//        MetalTableGateway.createMetal(dissolvedBy);
//        id = MetalTableGateway.findDissolvedBy(dissolvedBy).getId();
//        myMetal = new Metal(id, dissolvedBy);
//    }
//
//    public MetalMapper(long dissolvedBy) throws DataException {
//        MetalDTO metal = MetalTableGateway.findDissolvedBy(dissolvedBy);
//        myMetal = new Metal(metal.getId(), metal.getDissolvedBy());
//    }
//
    @Override
    public Metal getMyMetal() { return myMetal; }
}
