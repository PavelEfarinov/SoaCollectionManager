package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
import itmo.efarinov.soa.collectionmanager.error.BadFilterException;
import lombok.SneakyThrows;

public class CoordinatesFilterMapper implements CommonEntityFilterMapper<CoordinatesEntity> {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {

        if (dto.getPredicateAsType() == FilterPredicateType.LIKE) {
            return dto.toModel();
        }

        switch (dto.getFieldName()) {
            case "id":
            case "x":
            case "y":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException(dto.getFieldName() + " should be an int. Filter: '" + dto + "'");
                }
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}
