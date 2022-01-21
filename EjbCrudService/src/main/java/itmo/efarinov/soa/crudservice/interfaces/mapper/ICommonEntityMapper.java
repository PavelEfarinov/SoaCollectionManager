package itmo.efarinov.soa.crudservice.interfaces.mapper;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.dto.CommonDto;
import jakarta.ejb.Remote;

public interface ICommonEntityMapper<T extends CommonEntity<DT>, DT extends CommonDto>{
    T toModel(DT dto);
}
