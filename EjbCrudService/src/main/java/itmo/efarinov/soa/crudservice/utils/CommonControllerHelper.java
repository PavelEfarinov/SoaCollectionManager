package itmo.efarinov.soa.crudservice.utils;

import com.google.gson.reflect.TypeToken;
import itmo.efarinov.soa.crudservice.entity.CommonEntity;
import itmo.efarinov.soa.crudservice.filter.FilterPredicate;
import itmo.efarinov.soa.crudservice.filter.SortingOrder;
import itmo.efarinov.soa.crudservice.filter.error.BadFilterException;
import itmo.efarinov.soa.crudservice.interfaces.utils.ICommonControllerHelper;
import itmo.efarinov.soa.crudservice.interfaces.filter.IEntityFilterMapper;
import itmo.efarinov.soa.dto.FilterDto;
import itmo.efarinov.soa.json.gson.GsonObjectMapper;
import jakarta.ws.rs.BadRequestException;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommonControllerHelper<T extends CommonEntity, EMT extends IEntityFilterMapper<T>> implements ICommonControllerHelper {

    protected EMT entityFilterMapper;

    public CommonControllerHelper(EMT entityFilterMapper) {
        this.entityFilterMapper = entityFilterMapper;
    }

    public void log(String s) {
        System.out.println(s);
    }

    @SneakyThrows
    @Override
    public List<FilterPredicate<?>> getFilters(String queryParam) {
        log("FILTERS" + queryParam);
        Type listType = new TypeToken<ArrayList<FilterDto>>() {
        }.getType();
        List<FilterDto> resultDto = GsonObjectMapper.fromJson(queryParam, listType);
        List<FilterPredicate<?>> result = new ArrayList<>();
        if (resultDto != null) {
            for (FilterDto filterDto : resultDto) {
                FilterPredicate<?> filter = entityFilterMapper.mapToModel(filterDto);
                if (filter == null || filter.getPredicateType() == null || filter.getFieldName() == null) {
                    throw new BadFilterException("Could not create filter with the given params '" + filterDto.toString() + "'");
                }
                result.add(filter);
            }
        }
        return result;
    }

    @SneakyThrows
    @Override
    public SortingOrder getSortingOrder(String queryParam) {
        log("SORTING" + queryParam);
        try {
            SortingOrder result = GsonObjectMapper.fromJson(queryParam, SortingOrder.class);
            if (queryParam != null && (result == null || result.getType() == null)) {
                throw new BadRequestException("Could not parse sortOrder '" + queryParam + "'");
            }
            return result;
        } catch (Exception e) {
            throw new BadRequestException("Could not parse sortOrder '" + queryParam + "' due to " + e.getMessage());

        }

    }
}
