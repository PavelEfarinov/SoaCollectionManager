package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.crudservice.filter.WorkerFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.WorkerEntityMapper;
import itmo.efarinov.soa.crudservice.repository.CoordinatesRepository;
import itmo.efarinov.soa.crudservice.repository.OrganizationRepository;
import itmo.efarinov.soa.crudservice.repository.WorkerRepository;
import itmo.efarinov.soa.dto.WorkerDto;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workers/")
public class WorkerServlet extends CommonCrudServlet<WorkerEntity, WorkerDto> {
    private final OrganizationRepository organizationRepository;
    private final CoordinatesRepository coordinatesRepository;

    @Inject
    public WorkerServlet() {
        super(new WorkerFilterMapper(), new WorkerRepository(), new WorkerEntityMapper());
        organizationRepository = new OrganizationRepository();
        coordinatesRepository = new CoordinatesRepository();
    }

    @POST
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPostMethod(WorkerDto postArgs) {
        WorkerEntity worker = entityMapper.toModel(postArgs);

        if (postArgs.getOrganizationId() != null) {
            worker.setOrganization(organizationRepository.getById(postArgs.getOrganizationId()));
        }
        worker.setCoordinates(coordinatesRepository.getById(postArgs.getCoordinatesId()));
        entityRepository.save(worker);
        return Response.ok().entity(worker).build();
    }

    @PUT
    @Path("/{id}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPutMethod(@PathParam("id") Integer id, WorkerDto putArgs) {
        WorkerEntity worker = entityMapper.toModel(putArgs);
        worker.setId(id);
        if (putArgs.getOrganizationId() != null) {
            worker.setOrganization(organizationRepository.getById(putArgs.getOrganizationId()));
        }
        worker.setCoordinates(coordinatesRepository.getById(putArgs.getCoordinatesId()));
        entityRepository.update(worker);
        return Response.ok().entity(worker).build();
    }
}