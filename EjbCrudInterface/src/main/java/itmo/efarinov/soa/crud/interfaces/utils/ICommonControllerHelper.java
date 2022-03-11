package itmo.efarinov.soa.crud.interfaces.utils;

import itmo.efarinov.soa.crud.filter.FilterPredicate;
import itmo.efarinov.soa.crud.filter.SortingOrder;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.List;

public interface ICommonControllerHelper extends Serializable {

    void log(String s);

    @SneakyThrows
    List<FilterPredicate<?>> getFilters(String queryParam);

    @SneakyThrows
    SortingOrder getSortingOrder(String queryParam);
}
