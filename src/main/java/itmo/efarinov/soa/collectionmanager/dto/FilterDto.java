package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.error.BadRequestException;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicate;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicateType;
import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterDto extends CommonDto<FilterPredicate<String>> {
    private String fieldName;
    private String predicateType;
    private String fieldValue;

    @SneakyThrows
    public FilterPredicateType getPredicateAsType() {
        FilterPredicateType predicate = null;
        try {
            predicate = GsonObjectMapper.Mapper.fromJson(predicateType, FilterPredicateType.class);
        } catch (Exception ignored) {
        }
        if (predicate == null) {
            throw new BadRequestException("No such predicate: " + predicateType);
        }
        return predicate;
    }

    @Override
    public FilterPredicate<String> toModel() {
        return new FilterPredicate<>(getFieldName(), getPredicateAsType(), getFieldValue());
    }
}
