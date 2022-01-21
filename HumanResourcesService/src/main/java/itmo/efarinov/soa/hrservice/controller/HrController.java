package itmo.efarinov.soa.hrservice.controller;

import itmo.efarinov.soa.dto.ErrorDto;
import itmo.efarinov.soa.hrservice.facade.exceptions.NestedRequestException;
import itmo.efarinov.soa.hrservice.interfaces.IHrService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class HrController {

    @EJB
    IHrService hrService;

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
            hrService.moveEmployee(workerId, oldOrgId, newOrgId);
            return Response.ok().build();
        } catch (NestedRequestException e) {
            return Response.status(400).entity(ErrorDto.builder()
                    .error(NestedRequestException.class.getSimpleName())
                    .message(e.getMessage())
                    .build()).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(
                            ErrorDto.builder()
                                    .error(e.getClass().getSimpleName())
                                    .message(e.getMessage())
                                    .build()
                    ).build();
        }
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
            float newSalary = (float) hrService.indexSalary(workerId, indexCoefficient);
            return Response.ok().entity(newSalary).build();
        } catch (NestedRequestException e) {
            return Response.status(400)
                    .entity(
                            ErrorDto.builder()
                                    .error(NestedRequestException.class.getSimpleName())
                                    .message(e.getMessage())
                                    .build()
                    ).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(
                            ErrorDto.builder()
                                    .error(e.getClass().getSimpleName())
                                    .message(e.getMessage())
                                    .build()
                    ).build();
        }
    }
}
