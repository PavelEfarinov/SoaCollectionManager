package itmo.efarinov.soa.hrservice.facade;

import itmo.efarinov.soa.dto.WorkerDto;
import itmo.efarinov.soa.dto.get.GetWorkerDto;

public class WorkerFacade extends CrudFacade<GetWorkerDto, WorkerDto>{
    public WorkerFacade() {
        super("workers", GetWorkerDto.class);
    }
}
