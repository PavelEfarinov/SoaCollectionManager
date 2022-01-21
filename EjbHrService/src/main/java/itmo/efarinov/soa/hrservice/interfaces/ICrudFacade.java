package itmo.efarinov.soa.hrservice.interfaces;

import itmo.efarinov.soa.hrservice.facade.exceptions.NestedRequestException;

public interface ICrudFacade<T, DT> {
    T getById(int id) throws NestedRequestException;

    T updateById(int id, DT newEntity) throws NestedRequestException;
}
