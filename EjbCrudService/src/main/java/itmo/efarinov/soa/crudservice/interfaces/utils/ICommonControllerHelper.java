package itmo.efarinov.soa.crudservice.interfaces.utils;

import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import jakarta.ejb.Remote;
import lombok.SneakyThrows;

import java.util.List;

public interface ICommonControllerHelper{

    void log(String s);

    @SneakyThrows
    List<FilterPredicate<?>> getFilters(String queryParam);

    @SneakyThrows
    SortingOrder getSortingOrder(String queryParam);
}
