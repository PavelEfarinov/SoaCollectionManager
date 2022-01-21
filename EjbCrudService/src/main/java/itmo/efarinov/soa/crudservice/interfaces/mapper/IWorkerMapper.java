package itmo.efarinov.soa.crudservice.interfaces.mapper;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerMapper extends ICommonEntityMapper<WorkerEntity, WorkerDto> {
}
