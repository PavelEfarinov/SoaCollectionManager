package itmo.efarinov.soa.hrservice.controller;

import itmo.efarinov.soa.dto.ErrorDto;
import itmo.efarinov.soa.dto.WorkerDto;
import itmo.efarinov.soa.dto.get.GetOrganizationDto;
import itmo.efarinov.soa.dto.get.GetWorkerDto;
import itmo.efarinov.soa.hrservice.exceptions.NestedRequestException;
import itmo.efarinov.soa.hrservice.facade.OrganizationFacade;
import itmo.efarinov.soa.hrservice.facade.WorkerFacade;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class HrController {
    private final WorkerFacade workerFacade;
    private final OrganizationFacade orgFacade;

    public HrController() {
        workerFacade = new WorkerFacade();
        orgFacade = new OrganizationFacade();
    }

    @POST
    @Path("/move/{worker_id}/{old_organization_id}/{new_organization_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response moveWorkerToNewOrganization(
            @PathParam("worker_id") int workerId,
            @PathParam("old_organization_id") int oldOrgId,
            @PathParam("new_organization_id") int newOrgId) {

        if (workerId <= 0) {
            return Response.status(400).entity(ErrorDto.builder().error("BadRequest").message("WorkerId should be positive").build()).build();
        }
        if (oldOrgId <= 0) {
            return Response.status(400).entity(ErrorDto.builder().error("BadRequest").message("oldOrgId should be positive").build()).build();
        }

        if (newOrgId <= 0) {
            return Response.status(400).entity(ErrorDto.builder().error("BadRequest").message("newOrgId should be positive").build()).build();
        }

        try {
            GetWorkerDto worker = workerFacade.getById(workerId);

            if (worker.organization.id != oldOrgId) {
                throw new NestedRequestException("Worker with id " + workerId + " has different organization");
            }

            GetOrganizationDto oldOrg = orgFacade.getById(oldOrgId);
            GetOrganizationDto newOrg = orgFacade.getById(newOrgId);

            WorkerDto withNewSalary = WorkerDto
                    .builder()
                    .coordinatesId(worker.coordinates.id)
                    .organizationId(newOrg.id)
                    .salary(worker.salary)
                    .endDate(worker.endDate)
                    .name(worker.name)
                    .position(worker.position)
                    .startDate(worker.startDate)
                    .build();

            workerFacade.updateById(worker.id, withNewSalary);
        } catch (NestedRequestException e) {
            return Response.status(400).entity(ErrorDto.builder()
                    .error(NestedRequestException.class.getSimpleName())
                    .message(e.getMessage())
                    .build()).build();
        }
        return Response.ok().build();
    }


    @POST
    @Path("/index/{worker_id}/{coeff}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response indexWorkerSalary(
            @PathParam("worker_id") int workerId,
            @PathParam("coeff") float indexCoefficient) {

        if (workerId <= 0) {
            return Response.status(400).entity(ErrorDto.builder().error("BadRequest").message("WorkerId should be positive").build()).build();
        }
        if (indexCoefficient <= 0) {
            return Response.status(400).entity(ErrorDto.builder().error("BadRequest").message("Coefficient should be positive").build()).build();
        }

        try {
            GetWorkerDto worker = workerFacade.getById(workerId);

            WorkerDto withNewSalary = WorkerDto
                    .builder()
                    .coordinatesId(worker.coordinates.id)
                    .organizationId(worker.organization.id)
                    .salary(worker.salary * indexCoefficient)
                    .endDate(worker.endDate)
                    .name(worker.name)
                    .position(worker.position)
                    .startDate(worker.startDate)
                    .build();

            workerFacade.updateById(worker.id, withNewSalary);
        } catch (NestedRequestException e) {
            return Response.status(400)
                    .entity(
                            ErrorDto.builder()
                                    .error(NestedRequestException.class.getSimpleName())
                                    .message(e.getMessage())
                                    .build()
                    ).build();
        }
        return Response.ok().build();
    }
}
