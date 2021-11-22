package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;

public class CoordinatesRepository extends CommonCrudRepository<CoordinatesEntity>{
    public CoordinatesRepository() {
        super(CoordinatesEntity.class);
    }
}
