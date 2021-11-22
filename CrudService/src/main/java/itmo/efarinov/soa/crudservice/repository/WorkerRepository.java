package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

public class WorkerRepository extends CommonCrudRepository<WorkerEntity> {
    public WorkerRepository() {
        super(WorkerEntity.class);
    }

    public float countSalarySum() {
        EntityManager em = getEm();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Float> cq = cb.createQuery(Float.class);
        Root<WorkerEntity> root = cq.from(runtimeClass);

        Query q = em.createQuery(cq.select(cb.sum(root.get("salary"))));
        float result = (float) q.getSingleResult();
        em.close();
        return result;
    }
}
