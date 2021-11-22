package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.crudservice.filter.CoordinatesFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.CoordinatesEntityMapper;
import itmo.efarinov.soa.crudservice.repository.CoordinatesRepository;
import itmo.efarinov.soa.dto.CoordinatesDto;

import javax.ws.rs.Path;

@Path("/coordinates")
public class CoordinatesServlet extends CommonCrudServlet<CoordinatesEntity, CoordinatesDto> {
    public CoordinatesServlet() {
        super(new CoordinatesFilterMapper(), new CoordinatesRepository(), new CoordinatesEntityMapper());
    }
}
