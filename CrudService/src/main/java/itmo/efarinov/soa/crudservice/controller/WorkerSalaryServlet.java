package itmo.efarinov.soa.crudservice.controller;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.crudservice.filter.WorkerFilterMapper;
import itmo.efarinov.soa.crudservice.repository.WorkerRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/workers/salary/sum")
public class WorkerSalaryServlet extends CommonApplicationServlet<WorkerEntity> {
    protected WorkerRepository workerRepository = new WorkerRepository();

    public WorkerSalaryServlet() {
        super(new WorkerFilterMapper());
    }

    @GET
    public Response doGet() {
        return Response.ok().entity(workerRepository.countSalarySum()).build();
    }
}
