package itmo.efarinov.soa.crudservice.repository;

import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;

import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import java.util.List;

@Remote
public interface ICrudRepository<T> {
    void save(@Valid T entity);

    void update(@Valid T entity);

    void delete(T entity);

    void close();

    T getById(int id);

    void deleteById(int id);

    List<T> getByFilter(CriteriaQuery<?> query, int pageSize, int page);

    List<T> getByFilter(List<FilterPredicate<?>> query, int pageSize, int page, SortingOrder sortingOrder);

    long countByFilter(List<FilterPredicate<?>> query);
}