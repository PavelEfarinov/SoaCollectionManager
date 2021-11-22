package itmo.efarinov.soa.hrservice.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class HrController {
    @POST
    @Path("/move/{worker_id}/{old_organization_id}/{new_organization_id}")
    public Response moveWorkerToNewOrganization(
            @PathParam("worker_id") int workerId,
            @PathParam("old_organization_id") int oldOrgId,
            @PathParam("new_organization_id") int newOrgId) {
        return Response.ok().build();
    }


    @POST
    @Path("/index/{worker_id}/{coeff}")
    public Response indexWorkerSalary(
            @PathParam("worker_id") int workerId,
            @PathParam("coeff") float indexCoefficient) {
        return Response.ok().build();
    }
}
