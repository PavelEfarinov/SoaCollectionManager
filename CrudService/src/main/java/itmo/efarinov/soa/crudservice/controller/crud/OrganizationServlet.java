package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.crudservice.filter.OrganizationsFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.OrganizationEntityMapper;
import itmo.efarinov.soa.crudservice.repository.OrganizationRepository;
import itmo.efarinov.soa.dto.OrganizationDto;

import javax.ws.rs.Path;

@Path("/organizations")
public class OrganizationServlet extends CommonCrudServlet<OrganizationEntity, OrganizationDto> {

    public OrganizationServlet() {
        super(new OrganizationsFilterMapper(), new OrganizationRepository(), new OrganizationEntityMapper());
    }
}
