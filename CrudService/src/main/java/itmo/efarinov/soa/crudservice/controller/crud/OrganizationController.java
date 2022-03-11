package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crud.entity.OrganizationEntity;
import itmo.efarinov.soa.crud.interfaces.filter.IOrganizationFilterMapper;
import itmo.efarinov.soa.crud.interfaces.mapper.IOrganizationMapper;
import itmo.efarinov.soa.crud.interfaces.repository.IOrganizationRepository;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Path;

@Path("/organizations")
@Stateless
@LocalBean
public class OrganizationController extends CommonCrudController<
        OrganizationEntity,
        OrganizationDto,
        IOrganizationRepository,
        IOrganizationMapper,
        IOrganizationFilterMapper> {
    @EJB
    private IOrganizationRepository repository;
    @EJB
    private IOrganizationMapper mapper;
    @EJB
    private IOrganizationFilterMapper filterMapper;

    @PostConstruct
    public void init() {
        super.init(repository, mapper, filterMapper);
    }
}
