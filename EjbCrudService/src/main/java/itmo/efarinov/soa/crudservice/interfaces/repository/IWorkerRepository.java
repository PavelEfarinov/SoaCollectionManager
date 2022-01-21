package itmo.efarinov.soa.crudservice.interfaces.repository;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerRepository extends ICrudRepository<WorkerEntity>{
    public float countSalarySum();
}
