package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class CoordinatesRepository extends CommonCrudRepository<CoordinatesEntity> {
    public CoordinatesRepository() {
        super(CoordinatesEntity.class);
    }
}
