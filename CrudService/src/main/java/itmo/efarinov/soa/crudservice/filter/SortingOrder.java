package itmo.efarinov.soa.crudservice.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortingOrder {
    private SortingOrderType type;
    private String fieldName;

    public Order toOrder(CriteriaBuilder criteriaBuilder, Root<?> root) {
        Path<Object> field = root.get(fieldName);
        return type == SortingOrderType.ASC
                ? criteriaBuilder.asc(field)
                : criteriaBuilder.desc(field);
    }
}
