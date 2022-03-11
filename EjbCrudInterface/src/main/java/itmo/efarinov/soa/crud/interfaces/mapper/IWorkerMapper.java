package itmo.efarinov.soa.crud.interfaces.mapper;

import itmo.efarinov.soa.crud.entity.WorkerEntity;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerMapper extends ICommonEntityMapper<WorkerEntity, WorkerDto> {
}
