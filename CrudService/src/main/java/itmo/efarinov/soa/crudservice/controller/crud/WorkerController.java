package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.crudservice.filter.WorkerFilterMapper;
import itmo.efarinov.soa.crudservice.mapper.WorkerEntityMapper;
import itmo.efarinov.soa.crudservice.repository.CoordinatesRepository;
import itmo.efarinov.soa.crudservice.repository.OrganizationRepository;
import itmo.efarinov.soa.crudservice.repository.WorkerRepository;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

import javax.validation.Valid;

@Path("/workers/")
public class WorkerController extends CommonCrudController<WorkerEntity, WorkerDto> {
    @Inject

    private OrganizationRepository organizationRepository;
    @Inject

    private CoordinatesRepository coordinatesRepository;

    @Inject
    public WorkerController(WorkerFilterMapper filterMapper,
                            WorkerRepository workerRepository,
                            WorkerEntityMapper workerEntityMapper) {
        super(filterMapper, workerRepository, workerEntityMapper);
    }

    @POST
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPostMethod(@Valid WorkerDto postArgs) {
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

    @GET
    @Path("/salary/sum")
    public Response doGet() {
        return Response.ok().entity(((WorkerRepository) entityRepository).countSalarySum()).build();
    }

}