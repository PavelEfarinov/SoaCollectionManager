package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.OrganizationDto;
import itmo.efarinov.soa.dto.get.GetOrganizationDto;

public class OrganizationFacade extends CrudFacade<GetOrganizationDto, OrganizationDto> {

    public OrganizationFacade() {
        super("organizations", GetOrganizationDto.class);
    }
}
