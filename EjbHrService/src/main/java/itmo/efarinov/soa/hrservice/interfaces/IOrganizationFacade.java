package itmo.efarinov.soa.hrservice.interfaces;

import itmo.efarinov.soa.dto.OrganizationDto;
import itmo.efarinov.soa.dto.get.GetOrganizationDto;
import jakarta.ejb.Remote;

@Remote
public interface IOrganizationFacade extends ICrudFacade <GetOrganizationDto, OrganizationDto>{
}
