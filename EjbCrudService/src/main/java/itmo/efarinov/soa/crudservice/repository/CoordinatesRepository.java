package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crud.entity.CoordinatesEntity;
import itmo.efarinov.soa.crud.interfaces.repository.ICoordinatesRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class CoordinatesRepository extends CommonCrudRepository<CoordinatesEntity>  implements ICoordinatesRepository {
    public CoordinatesRepository() {
        super(CoordinatesEntity.class);
    }
}
