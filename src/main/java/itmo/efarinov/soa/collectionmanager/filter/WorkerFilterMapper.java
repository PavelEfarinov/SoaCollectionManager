package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkerFilterMapper implements CommonEntityFilterMapper<WorkerEntity> {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {

        if (dto.getPredicateAsType() == FilterPredicateType.LIKE) {
            return dto.toModel();
        }

        switch (dto.getFieldName()) {
            case "name":
            case "position":
                return dto.toModel();
            case "salary":
                return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Double.parseDouble(dto.getFieldValue()));
            case "id":
                return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Integer.parseInt(dto.getFieldValue()));
            case "creationDate":
            case "startDate":
                return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            case "endDate":
                return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_ZONED_DATE_TIME));
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}
