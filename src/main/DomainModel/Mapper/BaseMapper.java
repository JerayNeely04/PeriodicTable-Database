package DomainModel.Mapper;

import DomainModel.Base;
import DomainModel.BaseMapperInterface;
import datasource.BaseTableGateway;
import datasource.DataException;
import gatewayDTOs.BaseDTO;

public class BaseMapper implements BaseMapperInterface {
    private Base myBase;

    public BaseMapper(long id, long solute) throws DataException {
        BaseTableGateway.createBase(solute);
        id = BaseTableGateway.findSolute(solute).getId();
        myBase = new Base(id, solute);
    }

    public BaseMapper(long solute) throws DataException {
        BaseDTO base = BaseTableGateway.findSolute(solute);
        myBase = new Base(base.getId(), base.getSolute());
    }

    @Override
    public Base getMyBase() { return myBase; }
}
