package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.crudservice.interfaces.repository.ICoordinatesRepository;
import jakarta.ejb.Stateless;

@Stateless
public class CoordinatesRepository extends CommonCrudRepository<CoordinatesEntity>  implements ICoordinatesRepository {
    public CoordinatesRepository() {
        super(CoordinatesEntity.class);
    }
}
