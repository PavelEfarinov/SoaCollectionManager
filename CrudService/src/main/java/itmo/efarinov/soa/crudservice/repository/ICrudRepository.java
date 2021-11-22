package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ICrudRepository<T> {
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void close();

    T getById(int id);

    void deleteById(int id);

    List<T> getByFilter(CriteriaQuery<?> query, int pageSize, int page);

    List<T> getByFilter(List<FilterPredicate<?>> query, int pageSize, int page, SortingOrder sortingOrder);

    long countByFilter(List<FilterPredicate<?>> query);
}