package itmo.efarinov.soa.crud.interfaces.filter;

import itmo.efarinov.soa.crud.entity.WorkerEntity;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerFilterMapper extends IEntityFilterMapper<WorkerEntity>{
}
