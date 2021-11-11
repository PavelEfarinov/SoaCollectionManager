package itmo.efarinov.soa.collectionmanager.servlet;

import com.google.gson.reflect.TypeToken;
import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.CommonEntity;
import itmo.efarinov.soa.collectionmanager.error.BadFilterException;
import itmo.efarinov.soa.collectionmanager.error.BadRequestException;
import itmo.efarinov.soa.collectionmanager.filter.CommonEntityFilterMapper;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicate;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrder;
import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonApplicationServlet<T extends CommonEntity> extends HttpServlet {

    protected final CommonEntityFilterMapper<T> entityFilterMapper;

    public CommonApplicationServlet(CommonEntityFilterMapper<T> entityFilterMapper) {
        this.entityFilterMapper = entityFilterMapper;
    }

    @SneakyThrows
    protected List<FilterPredicate<?>> getFilters(HttpServletRequest request) {
        String queryParam = request.getParameter("filters");
        log("FILTERS" + queryParam);
        Type listType = new TypeToken<ArrayList<FilterDto>>() {
        }.getType();
        List<FilterDto> resultDto = GsonObjectMapper.Mapper.fromJson(queryParam, listType);
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
    protected SortingOrder getSortingOrder(HttpServletRequest request) {
        String queryParam = request.getParameter("sort");
        log("SORTING" + queryParam);
        SortingOrder result = GsonObjectMapper.Mapper.fromJson(queryParam, SortingOrder.class);
        if (queryParam != null && (result == null || result.getType() == null)) {
            throw new BadRequestException("Could not parse sortOrder '" + queryParam + "'");
        }
        return result;
    }

    @SneakyThrows
    protected int getIdFromLastPathPart(HttpServletRequest request) {
        try {
            String[] pathParts = request.getPathInfo().split("/");
            return Integer.parseInt(pathParts[pathParts.length - 1]);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Could not find id in given url %s", request.getPathInfo()));
        }
    }

    @SneakyThrows
    protected boolean hasPathSuffix(HttpServletRequest request) {
        try {
            String[] pathParts = request.getPathInfo().split("/");
            return pathParts.length > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
