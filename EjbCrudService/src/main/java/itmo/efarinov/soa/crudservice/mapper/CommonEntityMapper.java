package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.dto.CommonDto;
import jakarta.ejb.Remote;

@Remote
public interface CommonEntityMapper<T extends CommonEntity<DT>, DT extends CommonDto>{
    T toModel(DT dto);
}
