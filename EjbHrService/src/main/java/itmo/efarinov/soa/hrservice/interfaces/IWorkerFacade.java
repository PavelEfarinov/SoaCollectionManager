package itmo.efarinov.soa.hrservice.interfaces;

import itmo.efarinov.soa.dto.WorkerDto;
import itmo.efarinov.soa.dto.get.GetWorkerDto;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerFacade extends ICrudFacade<GetWorkerDto, WorkerDto> {
}
