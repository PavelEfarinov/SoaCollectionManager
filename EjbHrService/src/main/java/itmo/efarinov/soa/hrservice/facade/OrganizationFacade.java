package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.OrganizationDto;
import itmo.efarinov.soa.dto.get.GetOrganizationDto;
import itmo.efarinov.soa.hr.interfaces.IOrganizationFacade;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class OrganizationFacade extends CrudFacade<GetOrganizationDto, OrganizationDto> implements IOrganizationFacade {

    public OrganizationFacade() {
        super("organizations", GetOrganizationDto.class);
    }
}
