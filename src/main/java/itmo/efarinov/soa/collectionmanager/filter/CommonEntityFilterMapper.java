package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.CommonEntity;

public interface CommonEntityFilterMapper<T extends CommonEntity> {
    FilterPredicate<?> mapToModel(FilterDto dto);
}
