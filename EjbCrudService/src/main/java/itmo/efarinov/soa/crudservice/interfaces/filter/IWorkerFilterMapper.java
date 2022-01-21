package itmo.efarinov.soa.crudservice.interfaces.filter;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerFilterMapper extends IEntityFilterMapper<WorkerEntity>{
}
