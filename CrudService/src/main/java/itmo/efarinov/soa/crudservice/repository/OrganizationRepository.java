package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;

public class OrganizationRepository extends CommonCrudRepository<OrganizationEntity>{
    public OrganizationRepository() {
        super(OrganizationEntity.class);
    }
}
