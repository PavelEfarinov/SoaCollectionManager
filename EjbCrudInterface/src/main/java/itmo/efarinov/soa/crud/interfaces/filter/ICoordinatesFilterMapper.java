package itmo.efarinov.soa.crud.interfaces.filter;

import itmo.efarinov.soa.crud.entity.CoordinatesEntity;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesFilterMapper extends IEntityFilterMapper<CoordinatesEntity> {
}
