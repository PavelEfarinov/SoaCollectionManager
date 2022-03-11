package itmo.efarinov.soa.crud.interfaces.repository;

import itmo.efarinov.soa.crud.filter.FilterPredicate;
import itmo.efarinov.soa.crud.filter.SortingOrder;

import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public interface ICrudRepository<T> extends Serializable {
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