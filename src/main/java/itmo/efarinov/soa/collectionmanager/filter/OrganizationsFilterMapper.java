package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import itmo.efarinov.soa.collectionmanager.error.BadFilterException;
import lombok.SneakyThrows;

public class OrganizationsFilterMapper implements CommonEntityFilterMapper<OrganizationEntity> {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {

        if (dto.getPredicateAsType() == FilterPredicateType.LIKE) {
            return dto.toModel();
        }

        switch (dto.getFieldName()) {
            case "id":
            case "annualTurnover":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), dto.getPredicateAsType(), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException(dto.getFieldName() + " should be an int. Filter: '" + dto + "'");
                }
            case "fullName":
                return dto.toModel();
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}
