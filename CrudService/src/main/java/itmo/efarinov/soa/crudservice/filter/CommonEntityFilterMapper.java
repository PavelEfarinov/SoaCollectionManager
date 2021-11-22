package itmo.efarinov.soa.crudservice.filter;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.dto.FilterDto;

public interface CommonEntityFilterMapper<T extends CommonEntity> {
    FilterPredicate<?> mapToModel(FilterDto dto);
}
