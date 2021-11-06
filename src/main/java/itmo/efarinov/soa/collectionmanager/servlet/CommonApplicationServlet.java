package itmo.efarinov.soa.collectionmanager.servlet;

import com.google.gson.reflect.TypeToken;
import itmo.efarinov.soa.collectionmanager.dto.CommonEntityDto;
import itmo.efarinov.soa.collectionmanager.dto.FilterDto;
import itmo.efarinov.soa.collectionmanager.entity.CommonEntity;
import itmo.efarinov.soa.collectionmanager.error.BadRequestException;
import itmo.efarinov.soa.collectionmanager.filter.CommonEntityFilterMapper;
import itmo.efarinov.soa.collectionmanager.filter.FilterPredicate;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrder;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrderType;
import itmo.efarinov.soa.collectionmanager.repository.CommonCrudRepository;
import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonApplicationServlet<T extends CommonEntity, DT extends CommonEntityDto<T>> extends HttpServlet {

    protected CommonCrudRepository<T> entityRepository;
    protected final CommonEntityFilterMapper<T> entityFilterMapper;
    private final Class<T> runtimeEntityClass;
    private final Class<DT> runtimeDtoClass;

    public CommonApplicationServlet(CommonEntityFilterMapper<T> entityFilterMapper, Class<T> runtimeEntityClass, Class<DT> runtimeDtoClass) {
        this.entityFilterMapper = entityFilterMapper;
        this.runtimeEntityClass = runtimeEntityClass;
        this.runtimeDtoClass = runtimeDtoClass;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        entityRepository = new CommonCrudRepository<>(runtimeEntityClass);
    }

    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (!hasPathSuffix(request)) {
            getCollection(request, response);
        } else {
            getSingleItem(request, response);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int id = getIdFromLastPathPart(request);
        entityRepository.deleteById(id);
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        DT postArgs = GsonObjectMapper.Mapper.fromJson(request.getReader(), runtimeDtoClass);
        T entity = postArgs.toModel();

        entityRepository.save(entity);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(entity.toJson());
        }
    }

    @SneakyThrows
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        int id = getIdFromLastPathPart(request);
        DT postArgs = GsonObjectMapper.Mapper.fromJson(request.getReader(), runtimeDtoClass);
        T entity = postArgs.toModel();

        entity.setId(id);
        entityRepository.update(entity);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(entity.toJson());
        }
    }

    @SneakyThrows
    private void getSingleItem(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        int id = getIdFromLastPathPart(request);
        PrintWriter out = response.getWriter();
        out.println(entityRepository.getById(id).toJson());
    }

    @SneakyThrows
    private void getCollection(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String pageSizeParam = request.getParameter("pageSize");
        String pageParam = request.getParameter("page");
        SortingOrder orderBy;
        int pageSize, page;
        try {
            pageSize = Integer.parseInt(pageSizeParam);
            page = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) {
            page = 0;
            pageSize = 10;
        }

        orderBy = getSortingOrder(request);
        if (orderBy == null) {
            orderBy = SortingOrder.builder().fieldName("id").type(SortingOrderType.ASC).build();
        }

        PrintWriter out = response.getWriter();
        List<T> list = entityRepository.getByFilter(getFilters(request), pageSize, page, orderBy);
        out.print(GsonObjectMapper.Mapper.toJson(list));
    }

    protected List<FilterPredicate<?>> getFilters(HttpServletRequest request) {
        String queryParam = request.getParameter("filters");
        log(queryParam);
        Type listType = new TypeToken<ArrayList<FilterDto>>() {
        }.getType();
        List<FilterDto> resultDto = GsonObjectMapper.Mapper.fromJson(queryParam, listType);
        List<FilterPredicate<?>> result = new ArrayList<>();
        if (resultDto != null) {
            for (FilterDto filterDto : resultDto) {
                result.add(entityFilterMapper.mapToModel(filterDto));
            }
        }
        return result;
    }

    protected SortingOrder getSortingOrder(HttpServletRequest request) {
        String queryParam = request.getParameter("sort");
        log(queryParam);
        return GsonObjectMapper.Mapper.fromJson(queryParam, SortingOrder.class);
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
