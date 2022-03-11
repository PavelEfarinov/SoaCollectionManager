package itmo.efarinov.soa.crudservice.controller.crud;

import itmo.efarinov.soa.crud.entity.WorkerEntity;
import itmo.efarinov.soa.crud.interfaces.filter.IWorkerFilterMapper;
import itmo.efarinov.soa.crud.interfaces.mapper.IWorkerMapper;
import itmo.efarinov.soa.crud.interfaces.repository.ICoordinatesRepository;
import itmo.efarinov.soa.crud.interfaces.repository.IOrganizationRepository;
import itmo.efarinov.soa.crud.interfaces.repository.IWorkerRepository;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

import javax.validation.Valid;

@Path("/workers/")
@Stateless
@LocalBean
public class WorkerController extends CommonCrudController<
        WorkerEntity,
        WorkerDto,
        IWorkerRepository,
        IWorkerMapper,
        IWorkerFilterMapper> {
    @EJB
    private IOrganizationRepository organizationRepository;
    @EJB
    private ICoordinatesRepository coordinatesRepository;
    @EJB
    private IWorkerRepository repository;
    @EJB
    private IWorkerMapper mapper;
    @EJB
    private IWorkerFilterMapper filterMapper;

    @PostConstruct
    public void init() {
        super.init(repository, mapper, filterMapper);
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
        return Response.ok().entity(repository.countSalarySum()).build();
    }

}