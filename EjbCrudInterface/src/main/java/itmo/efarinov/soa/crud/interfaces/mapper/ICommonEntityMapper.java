package itmo.efarinov.soa.crud.interfaces.mapper;

import itmo.efarinov.soa.crud.entity.CommonEntity;
import itmo.efarinov.soa.dto.CommonDto;

import java.io.Serializable;

public interface ICommonEntityMapper<T extends CommonEntity<DT>, DT extends CommonDto> extends Serializable {
    T toModel(DT dto);
}
