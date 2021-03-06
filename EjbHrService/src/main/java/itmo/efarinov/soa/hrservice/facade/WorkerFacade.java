package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.WorkerDto;
import itmo.efarinov.soa.dto.get.GetWorkerDto;
import itmo.efarinov.soa.hr.interfaces.IWorkerFacade;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class WorkerFacade extends CrudFacade<GetWorkerDto, WorkerDto> implements IWorkerFacade {
    public WorkerFacade() {
        super("workers", GetWorkerDto.class);
    }
}
