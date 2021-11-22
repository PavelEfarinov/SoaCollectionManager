package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.dto.CommonDto;

public abstract class CommonEntityMapper<T extends CommonEntity<DT>, DT extends CommonDto>{
    public abstract T toModel(DT dto);
}
