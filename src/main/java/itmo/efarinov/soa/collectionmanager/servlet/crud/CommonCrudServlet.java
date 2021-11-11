package itmo.efarinov.soa.collectionmanager.servlet.crud;

import itmo.efarinov.soa.collectionmanager.dto.CommonEntityDto;
import itmo.efarinov.soa.collectionmanager.entity.CommonEntity;
import itmo.efarinov.soa.collectionmanager.error.BadRequestException;
import itmo.efarinov.soa.collectionmanager.filter.CommonEntityFilterMapper;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrder;
import itmo.efarinov.soa.collectionmanager.filter.SortingOrderType;
import itmo.efarinov.soa.collectionmanager.repository.CommonCrudRepository;
import itmo.efarinov.soa.collectionmanager.servlet.CommonApplicationServlet;
import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public abstract class CommonCrudServlet<T extends CommonEntity, DT extends CommonEntityDto<T>> extends CommonApplicationServlet<T> {

    protected CommonCrudRepository<T> entityRepository;
    private final Class<T> runtimeEntityClass;
    private final Class<DT> runtimeDtoClass;

    public CommonCrudServlet(CommonEntityFilterMapper<T> entityFilterMapper, Class<T> runtimeEntityClass, Class<DT> runtimeDtoClass) {
        super(entityFilterMapper);
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
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                throw new BadRequestException("Could not parse page '" + pageParam + "'");
            }
        } else {
            page = 0;
        }

        if (pageSizeParam != null) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                throw new BadRequestException("Could not parse pageSize '" + pageSizeParam + "'");
            }
        } else {
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
}
