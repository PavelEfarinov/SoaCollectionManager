package itmo.efarinov.soa.collectionmanager.repository;

import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;

import javax.persistence.Query;
import javax.persistence.criteria.*;

public class WorkerRepository extends CommonCrudRepository<WorkerEntity> {
    public WorkerRepository() {
        super(WorkerEntity.class);
    }

    public float countSalarySum() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Float> cq = cb.createQuery(Float.class);
        Root<WorkerEntity> root = cq.from(runtimeClass);

        Query q = em.createQuery(cq.select(cb.sum(root.get("salary"))));

        return (float) q.getSingleResult();
    }
}
