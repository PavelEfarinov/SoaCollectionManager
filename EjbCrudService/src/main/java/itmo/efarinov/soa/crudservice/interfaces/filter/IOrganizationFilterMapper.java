package itmo.efarinov.soa.crudservice.interfaces.filter;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationFilterMapper extends IEntityFilterMapper<OrganizationEntity>{
}
