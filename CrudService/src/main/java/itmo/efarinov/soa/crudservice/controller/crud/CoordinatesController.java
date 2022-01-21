package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.crudservice.filter.CoordinatesFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.CoordinatesEntityMapper;
import itmo.efarinov.soa.crudservice.repository.CoordinatesRepository;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Path;

@Path("/coordinates")
public class CoordinatesController extends CommonCrudController<CoordinatesEntity, CoordinatesDto> {
    @Inject
    public CoordinatesController( CoordinatesFilterMapper filterMapper,
                                  CoordinatesRepository repository,
                                  CoordinatesEntityMapper entityMapper) {
        super(filterMapper, repository, entityMapper);
    }
}
