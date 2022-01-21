package itmo.efarinov.soa.crudservice.interfaces.repository;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationRepository extends ICrudRepository<OrganizationEntity>{
}
