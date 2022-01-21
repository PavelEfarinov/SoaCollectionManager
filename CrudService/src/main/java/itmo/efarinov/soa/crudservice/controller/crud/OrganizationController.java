package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.crudservice.interfaces.filter.ICoordinatesFilterMapper;
import itmo.efarinov.soa.crudservice.interfaces.filter.IOrganizationFilterMapper;
import itmo.efarinov.soa.crudservice.interfaces.filter.IWorkerFilterMapper;
import itmo.efarinov.soa.crudservice.interfaces.mapper.ICoordinatesMapper;
import itmo.efarinov.soa.crudservice.interfaces.mapper.IOrganizationMapper;
import itmo.efarinov.soa.crudservice.interfaces.mapper.IWorkerMapper;
import itmo.efarinov.soa.crudservice.interfaces.repository.ICoordinatesRepository;
import itmo.efarinov.soa.crudservice.interfaces.repository.IOrganizationRepository;
import itmo.efarinov.soa.crudservice.interfaces.repository.IWorkerRepository;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Path;

@Path("/organizations")
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
