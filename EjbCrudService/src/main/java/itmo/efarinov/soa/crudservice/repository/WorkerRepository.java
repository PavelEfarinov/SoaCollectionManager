package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.crudservice.interfaces.repository.IWorkerRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class WorkerRepository extends CommonCrudRepository<WorkerEntity> implements IWorkerRepository {
    public WorkerRepository() {
        super(WorkerEntity.class);
    }

    @Override
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
