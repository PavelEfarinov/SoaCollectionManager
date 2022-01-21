package itmo.efarinov.soa.crudservice.interfaces.mapper;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationMapper extends ICommonEntityMapper<OrganizationEntity, OrganizationDto> {
}
