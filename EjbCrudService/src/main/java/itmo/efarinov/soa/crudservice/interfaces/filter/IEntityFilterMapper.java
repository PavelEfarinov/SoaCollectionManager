package itmo.efarinov.soa.crudservice.interfaces.filter;

import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.dto.FilterDto;
import jakarta.ejb.Remote;

public interface IEntityFilterMapper<T extends CommonEntity> {
    FilterPredicate<?> mapToModel(FilterDto dto);
}
