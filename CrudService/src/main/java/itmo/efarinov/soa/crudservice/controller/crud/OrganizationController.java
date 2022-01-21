package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.crudservice.filter.OrganizationsFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.OrganizationEntityMapper;
import itmo.efarinov.soa.crudservice.repository.OrganizationRepository;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Path;

@Path("/organizations")
public class OrganizationController extends CommonCrudController<OrganizationEntity, OrganizationDto> {
    @Inject
    public OrganizationController( OrganizationsFilterMapper filterMapper,
                                   OrganizationRepository organizationRepository,
                                   OrganizationEntityMapper entityMapper) {
        super(filterMapper, organizationRepository, entityMapper);
    }
}
