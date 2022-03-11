package itmo.efarinov.soa.crud.interfaces.filter;

import itmo.efarinov.soa.crud.entity.CommonEntity;
import itmo.efarinov.soa.crud.filter.FilterPredicate;
import itmo.efarinov.soa.dto.FilterDto;

import java.io.Serializable;

public interface IEntityFilterMapper<T extends CommonEntity> extends Serializable {
    FilterPredicate<?> mapToModel(FilterDto dto);
}
