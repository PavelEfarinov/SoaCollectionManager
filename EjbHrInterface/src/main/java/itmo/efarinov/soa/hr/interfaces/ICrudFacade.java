package itmo.efarinov.soa.hr.interfaces;

import itmo.efarinov.soa.exceptions.NestedRequestException;
import jakarta.ejb.Remote;

@Remote
public interface ICrudFacade<T, DT> {
    T getById(int id) throws NestedRequestException;

    T updateById(int id, DT newEntity) throws NestedRequestException;
}
