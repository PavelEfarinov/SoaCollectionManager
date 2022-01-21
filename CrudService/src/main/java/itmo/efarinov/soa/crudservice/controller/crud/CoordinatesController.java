package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.crudservice.interfaces.filter.ICoordinatesFilterMapper;
import itmo.efarinov.soa.crudservice.interfaces.mapper.ICoordinatesMapper;
import itmo.efarinov.soa.crudservice.interfaces.repository.ICoordinatesRepository;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;

@Path("/coordinates")
public class CoordinatesController extends CommonCrudController<
        CoordinatesEntity,
        CoordinatesDto,
        ICoordinatesRepository,
        ICoordinatesMapper,
        ICoordinatesFilterMapper> {

    @EJB
    private ICoordinatesRepository repository;
    @EJB
    private ICoordinatesMapper mapper;
    @EJB
    private ICoordinatesFilterMapper filterMapper;

    @PostConstruct
    public void init() {
        super.init(repository, mapper, filterMapper);
    }
}
