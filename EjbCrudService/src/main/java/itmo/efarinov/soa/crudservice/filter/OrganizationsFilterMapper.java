package itmo.efarinov.soa.crudservice.filter;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.crudservice.filter.error.BadFilterException;
import itmo.efarinov.soa.dto.FilterDto;
import jakarta.ejb.LocalBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import lombok.SneakyThrows;

@Stateless
@LocalBean
public class OrganizationsFilterMapper implements CommonEntityFilterMapper<OrganizationEntity> {
    @Override
    @SneakyThrows
    public FilterPredicate<?> mapToModel(FilterDto dto) {

        if (FilterPredicate.dtoAsPredicateType(dto) == FilterPredicateType.LIKE) {
            return FilterPredicate.dtoToModel(dto);
        }

        switch (dto.getFieldName()) {
            case "id":
            case "annualTurnover":
                try {
                    return new FilterPredicate<>(dto.getFieldName(), FilterPredicate.dtoAsPredicateType(dto), Integer.parseInt(dto.getFieldValue()));
                } catch (NumberFormatException e) {
                    throw new BadFilterException(dto.getFieldName() + " should be an int. Filter: '" + dto + "'");
                }
            case "fullName":
                return FilterPredicate.dtoToModel(dto);
            default:
                throw new NoSuchFieldException(String.format("No such field '%s' to filter", dto.getFieldName()));
        }
    }
}