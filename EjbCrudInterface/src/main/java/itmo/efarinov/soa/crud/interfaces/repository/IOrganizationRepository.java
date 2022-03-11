package itmo.efarinov.soa.crud.interfaces.repository;

import itmo.efarinov.soa.crud.entity.OrganizationEntity;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationRepository extends ICrudRepository<OrganizationEntity>{
}
