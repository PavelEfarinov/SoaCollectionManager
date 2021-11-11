package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import itmo.efarinov.soa.collectionmanager.error.BadFilterException;
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
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Double.parseDouble(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("salary should be a double. Filter: '" + dto + "'");
                }
            case "id":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("id should be an int. Filter: '" + dto + "'");
                }
            case "creationDate":
            case "startDate":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("date should be in ISO_LOCAL_DATE_TIME. Filter: '" + dto + "'");
                }
            case "endDate":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_ZONED_DATE_TIME));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("end date should be in ISO_ZONED_DATE_TIME. Filter: '" + dto + "'");
                }
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}
