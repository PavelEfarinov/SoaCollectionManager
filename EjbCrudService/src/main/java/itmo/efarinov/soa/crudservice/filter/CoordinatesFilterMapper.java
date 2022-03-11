package itmo.efarinov.soa.crudservice.filter;

import itmo.efarinov.soa.crud.filter.FilterPredicate;
import itmo.efarinov.soa.crud.filter.FilterPredicateType;
import itmo.efarinov.soa.crud.filter.error.BadFilterException;
import itmo.efarinov.soa.crud.interfaces.filter.ICoordinatesFilterMapper;
import itmo.efarinov.soa.dto.FilterDto;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import lombok.SneakyThrows;

@Stateless
public class CoordinatesFilterMapper implements ICoordinatesFilterMapper {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {

        if (FilterPredicate.dtoAsPredicateType(dto) == FilterPredicateType.LIKE) {
            return FilterPredicate.dtoToModel(dto);
        }

        switch (dto.getFieldName()) {
            case "id":
            case "x":
            case "y":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException(dto.getFieldName() + " should be an int. Filter: '" + dto + "'");
                }
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}
