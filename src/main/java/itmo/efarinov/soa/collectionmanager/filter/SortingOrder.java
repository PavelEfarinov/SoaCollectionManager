package itmo.efarinov.soa.collectionmanager.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortingOrder {
    private SortingOrderType type;
    private String fieldName;

    public Order toOrder(CriteriaBuilder criteriaBuilder, Root<?> root) {
        return type == SortingOrderType.ASC
                ? criteriaBuilder.asc(root.get(fieldName))
                : criteriaBuilder.desc(root.get(fieldName));
    }
}
