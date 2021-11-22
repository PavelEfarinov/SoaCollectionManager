package itmo.efarinov.soa.crudservice.filter;

import itmo.efarinov.soa.crudservice.error.BadRequestException;
import itmo.efarinov.soa.dto.FilterDto;
import itmo.efarinov.soa.json.JsonableModel;
import itmo.efarinov.soa.json.gson.GsonObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class FilterPredicate<T extends Comparable<T>> extends JsonableModel {
    private final String fieldName;
    private final FilterPredicateType predicateType;
    private final T fieldValue;

    public FilterPredicate(String fieldName, FilterPredicateType predicateType, T fieldValue) {
        this.fieldName = fieldName;
        this.predicateType = predicateType;
        this.fieldValue = fieldValue;
    }

    public Predicate getQueryFilterPredicate(CriteriaBuilder criteriaBuilder, Root<?> root) {
        Predicate p;
        switch (predicateType) {
            case EQ:
                p = criteriaBuilder.equal(root.get(fieldName), fieldValue);
                break;
            case LT:
                p = criteriaBuilder.lessThan(root.get(fieldName), fieldValue);
                break;
            case GT:
                p = criteriaBuilder.greaterThan(root.get(fieldName), fieldValue);
                break;
            case LIKE:
                p = criteriaBuilder.like(root.get(fieldName).as(String.class), "%" + fieldValue + "%");
                break;
            default:
                throw new IllegalStateException("Unexpected predicate type: " + predicateType);
        }
        return p;
    }

    @SneakyThrows
    public static FilterPredicateType dtoAsPredicateType(FilterDto dto) {
        FilterPredicateType predicate = null;
        try {
            predicate = GsonObjectMapper.fromJson(dto.predicateType, FilterPredicateType.class);
        } catch (Exception ignored) {
        }
        if (predicate == null) {
            throw new BadRequestException("No such predicate: " + dto.predicateType);
        }
        return predicate;
    }

    public static FilterPredicate<String> dtoToModel(FilterDto dto) {
        return new FilterPredicate<>(dto.getFieldName(), dtoAsPredicateType(dto), dto.getFieldValue());
    }
}
