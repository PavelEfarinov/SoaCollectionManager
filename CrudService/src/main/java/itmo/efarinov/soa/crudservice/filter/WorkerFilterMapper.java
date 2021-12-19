package itmo.efarinov.soa.crudservice.filter;

import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.dto.FilterDto;
import itmo.efarinov.soa.crudservice.error.BadFilterException;
import itmo.efarinov.soa.crudservice.error.BadRequestException;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkerFilterMapper implements CommonEntityFilterMapper<WorkerEntity> {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {
        if(dto.getFieldValue() == null)
        {
            throw new BadRequestException(dto.getFieldName() + " should be not null");
        }

        if (FilterPredicate.dtoAsPredicateType(dto) == FilterPredicateType.LIKE) {
            return FilterPredicate.dtoToModel(dto);
        }

        switch (dto.getFieldName()) {
            case "name":
            case "position":
                return FilterPredicate.dtoToModel(dto);
            case "salary":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), Double.parseDouble(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("salary should be a double. Filter: '" + dto + "'");
                }
            case "id":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("id should be an int. Filter: '" + dto + "'");
                }
            case "creationDate":
            case "startDate":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("date should be in ISO_LOCAL_DATE_TIME. Filter: '" + dto + "'");
                }
            case "endDate":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), LocalDateTime.parse(dto.getFieldValue(), DateTimeFormatter.ISO_ZONED_DATE_TIME));
                } catch (NumberFormatException e) {
                    throw new BadFilterException("end date should be in ISO_ZONED_DATE_TIME. Filter: '" + dto + "'");
                }
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}