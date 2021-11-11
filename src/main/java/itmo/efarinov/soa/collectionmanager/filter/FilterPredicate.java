package itmo.efarinov.soa.collectionmanager.filter;

import itmo.efarinov.soa.collectionmanager.utils.JsonableModel;
import lombok.Data;

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

}
