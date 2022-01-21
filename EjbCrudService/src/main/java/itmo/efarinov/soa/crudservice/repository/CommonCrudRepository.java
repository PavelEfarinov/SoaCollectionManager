package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import itmo.efarinov.soa.crudservice.filter.error.MaxPageNumberExceededException;
import itmo.efarinov.soa.crudservice.utils.SessionFactoryBuilder;
import jakarta.ejb.EJB;
import lombok.SneakyThrows;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.List;

public class CommonCrudRepository<T> implements ICrudRepository<T> {
    protected EntityManager getEm() {
        return SessionFactoryBuilder.getSessionFactory().createEntityManager();
    }

    protected final Class<T> runtimeClass;

    public CommonCrudRepository(Class<T> runtimeClass) {
        this.runtimeClass = runtimeClass;
    }

    @Override
    public void save(@Valid T worker) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.persist(worker);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(@Valid T worker) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.merge(worker);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(T entity) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteById(int id) {
        T entity = getById(id);
        delete(entity);
    }

    @Override
    public void close() {

    }

    @Override
    public T getById(int id) {
        EntityManager em = getEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(runtimeClass);
        Root<T> root = q.from(runtimeClass);
        q.select(root).where(cb.equal(root.get("id"), id));
        T result = em.createQuery(q).getSingleResult();
        em.close();
        return result;
    }

    @Override
    public List<T> getByFilter(CriteriaQuery<?> query, int pageSize, int page) {
        EntityManager em = getEm();
        Query q = em.createQuery(query);
        q.setFirstResult(pageSize * page);
        q.setMaxResults(pageSize);
        List<T> result = q.getResultList();
        em.close();
        return result;
    }

    @SneakyThrows
    @Override
    public List<T> getByFilter(List<FilterPredicate<?>> query, int pageSize, int page, SortingOrder sortingOrder) {

        long totalEntities = countByFilter(query);
        if (page * pageSize > totalEntities) {
            throw new MaxPageNumberExceededException("'" + page * pageSize + "' is bigger then total amount of entities in db (" + totalEntities + ")");
        }

        EntityManager em = getEm();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(runtimeClass);
        Root<T> root = cq.from(runtimeClass);
        Predicate[] predicates = new Predicate[query.size()];
        for (int i = 0; i < query.size(); ++i) {
            System.out.println("FILTERING " + query.get(i).toJson());
            predicates[i] = query.get(i).getQueryFilterPredicate(cb, root);
        }

        Query q = em.createQuery(cq.select(root).where(predicates).orderBy(sortingOrder.toOrder(cb, root)));

        q.setFirstResult(pageSize * page);
        q.setMaxResults(pageSize);

        List<T> result = q.getResultList();
        em.close();
        return result;
    }

    @Override
    public long countByFilter(List<FilterPredicate<?>> query) {
        EntityManager em = getEm();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(runtimeClass);
        Expression<Long> countExpr = cb.count(root);
        Predicate[] predicates = new Predicate[query.size()];
        for (int i = 0; i < query.size(); ++i) {
            System.out.println("COUNTING " + query.get(i).toJson());
            predicates[i] = query.get(i).getQueryFilterPredicate(cb, root);
        }

        Query q = em.createQuery(cq.select(countExpr).where(predicates));
        long result = (long) q.getSingleResult();
        em.close();
        return result;
    }
}
