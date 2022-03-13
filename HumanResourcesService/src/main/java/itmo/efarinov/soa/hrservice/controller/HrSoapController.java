package itmo.efarinov.soa.hrservice.controller;

import itmo.efarinov.soa.dto.ErrorDto;
import itmo.efarinov.soa.exceptions.NestedRequestException;
import itmo.efarinov.soa.hr.interfaces.IHrService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.SneakyThrows;

@WebService
public class HrSoapController {

    public HrSoapController(IHrService hrService)
    {
        this.hrService = hrService;
    }

    IHrService hrService;

    @WebMethod
    @SneakyThrows
    public void moveWorkerToNewOrganization(
            int workerId,
            int oldOrgId,
            int newOrgId) {

        if (workerId <= 0) {
            throw new NestedRequestException(ErrorDto.builder().error("BadRequest").message("WorkerId should be positive").build().toJson());
        }
        if (oldOrgId <= 0) {
            throw new NestedRequestException(ErrorDto.builder().error("BadRequest").message("oldOrgId should be positive").build().toJson());
        }

        if (newOrgId <= 0) {
            throw new NestedRequestException(ErrorDto.builder().error("BadRequest").message("newOrgId should be positive").build().toJson());
        }

        try {
            hrService.moveEmployee(workerId, oldOrgId, newOrgId);
        } catch (NestedRequestException e) {
            throw new NestedRequestException(ErrorDto.builder()
                    .error(NestedRequestException.class.getSimpleName())
                    .message(e.getMessage()).build().toJson());
        } catch (Exception e) {
            throw new NestedRequestException(ErrorDto.builder()
                    .error(e.getClass().getSimpleName())
                    .message(e.getMessage())
                    .build().toJson());
        }
    }

    @WebMethod
    @SneakyThrows
    public float indexWorkerSalary(
            int workerId,
            float indexCoefficient) {

        if (workerId <= 0) {
            throw new NestedRequestException(ErrorDto.builder().error("BadRequest").message("WorkerId should be positive").build().toJson());
        }
        if (indexCoefficient <= 0) {
            throw new NestedRequestException(ErrorDto.builder().error("BadRequest").message("Coefficient should be positive").build().toJson());
        }

        try {
            return (float) hrService.indexSalary(workerId, indexCoefficient);
        } catch (NestedRequestException e) {
            throw new NestedRequestException(ErrorDto.builder().error(NestedRequestException.class.getSimpleName()).message(e.getMessage()).build().toJson());
        } catch (Exception e) {
            throw new NestedRequestException(ErrorDto.builder()
                    .error(e.getClass().getSimpleName())
                    .message(e.getMessage())
                    .build().toJson());
        }
    }
}
