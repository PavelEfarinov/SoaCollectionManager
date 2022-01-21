package itmo.efarinov.soa.crudservice.interfaces.repository;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesRepository extends ICrudRepository<CoordinatesEntity> {
}
