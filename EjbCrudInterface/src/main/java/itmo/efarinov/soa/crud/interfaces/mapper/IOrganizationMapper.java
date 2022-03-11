package itmo.efarinov.soa.crud.interfaces.mapper;

import itmo.efarinov.soa.crud.entity.OrganizationEntity;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationMapper extends ICommonEntityMapper<OrganizationEntity, OrganizationDto> {
}
