package itmo.efarinov.soa.collectionmanager.repository;

import itmo.efarinov.soa.collectionmanager.error.MaxPageNumberExceededException;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrder;
import itmo.efarinov.soa.collectionmanager.utils.SessionFactoryBuilder;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicate;
import lombok.SneakyThrows;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class CommonCrudRepository<T> implements ICrudRepository<T> {
    protected final EntityManager em;
    protected final Class<T> runtimeClass;

    public CommonCrudRepository(Class<T> runtimeClass) {
        this.runtimeClass = runtimeClass;
        em = SessionFactoryBuilder.getSessionFactory().createEntityManager();
    }

    @Override
    public void save(T worker) {
        em.getTransaction().begin();
        em.persist(worker);
        em.getTransaction().commit();
    }

    @Override
    public void update(T worker) {
        em.getTransaction().begin();
        em.merge(worker);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) {
        T entity = getById(id);
        delete(entity);
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public T getById(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(runtimeClass);
        Root<T> root = q.from(runtimeClass);
        q.select(root).where(cb.equal(root.get("id"), id));
        return em.createQuery(q).getSingleResult();
    }

    @Override
    public List<T> getByFilter(CriteriaQuery<?> query, int pageSize, int page) {
        Query q = em.createQuery(query);
        q.setFirstResult(pageSize * page);
        q.setMaxResults(pageSize);
        return (List<T>) q.getResultList();
    }

    @SneakyThrows
    @Override
    public List<T> getByFilter(List<FilterPredicate<?>> query, int pageSize, int page, SortingOrder sortingOrder) {

        long totalEntities = countByFilter(query);
        if (page * pageSize > totalEntities) {
            throw new MaxPageNumberExceededException("'" + page * pageSize + "' is bigger then total amount of entities in db (" + totalEntities + ")");
        }

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

        return (List<T>) q.getResultList();
    }

    @Override
    public long countByFilter(List<FilterPredicate<?>> query) {
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

        return (long) q.getSingleResult();
    }
}
