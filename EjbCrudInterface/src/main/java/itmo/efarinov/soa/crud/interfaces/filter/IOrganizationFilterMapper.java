package itmo.efarinov.soa.crud.interfaces.filter;

import itmo.efarinov.soa.crud.entity.OrganizationEntity;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationFilterMapper extends IEntityFilterMapper<OrganizationEntity>{
}
