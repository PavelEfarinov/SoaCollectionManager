package itmo.efarinov.soa.crudservice.interfaces.filter;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesFilterMapper extends IEntityFilterMapper<CoordinatesEntity> {
}
