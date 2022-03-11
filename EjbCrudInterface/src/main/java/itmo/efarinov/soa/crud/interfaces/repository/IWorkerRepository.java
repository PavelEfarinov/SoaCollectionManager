package itmo.efarinov.soa.crud.interfaces.repository;

import itmo.efarinov.soa.crud.entity.WorkerEntity;
import jakarta.ejb.Remote;

@Remote
public interface IWorkerRepository extends ICrudRepository<WorkerEntity>{
    public float countSalarySum();
}
