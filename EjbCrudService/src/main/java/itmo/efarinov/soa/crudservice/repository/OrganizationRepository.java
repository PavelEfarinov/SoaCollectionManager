package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.crudservice.interfaces.repository.IOrganizationRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class OrganizationRepository extends CommonCrudRepository<OrganizationEntity> implements IOrganizationRepository {
    public OrganizationRepository() {
        super(OrganizationEntity.class);
    }
}
