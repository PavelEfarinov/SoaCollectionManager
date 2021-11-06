package itmo.efarinov.soa.collectionmanager.repository;

import itmo.efarinov.soa.collectionmanager.filter.SortingOrder;
import itmo.efarinov.soa.collectionmanager.utils.SessionFactoryBuilder;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CommonCrudRepository<T> implements ICrudRepository<T> {
    private final EntityManager em;
    private final Class<T> runtimeClass;

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

    @Override
    public List<T> getByFilter(List<FilterPredicate<?>> query, int pageSize, int page, SortingOrder sortingOrder) {

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
}
