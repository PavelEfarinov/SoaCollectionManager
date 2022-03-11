package itmo.efarinov.soa.crud.interfaces.repository;

import itmo.efarinov.soa.crud.entity.CoordinatesEntity;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesRepository extends ICrudRepository<CoordinatesEntity> {
}
