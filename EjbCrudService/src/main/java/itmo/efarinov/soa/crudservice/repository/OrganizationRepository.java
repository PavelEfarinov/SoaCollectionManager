package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import jakarta.ejb.LocalBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
@Stateless
@LocalBean
public class OrganizationRepository extends CommonCrudRepository<OrganizationEntity>{
    public OrganizationRepository() {
        super(OrganizationEntity.class);
    }
}
