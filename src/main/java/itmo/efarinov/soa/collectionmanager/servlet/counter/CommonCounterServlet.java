package itmo.efarinov.soa.collectionmanager.servlet.counter;

import itmo.efarinov.soa.collectionmanager.entity.CommonEntity;
import itmo.efarinov.soa.collectionmanager.filter.CommonEntityFilterMapper;
import itmo.efarinov.soa.collectionmanager.repository.CommonCrudRepository;
import itmo.efarinov.soa.collectionmanager.servlet.CommonApplicationServlet;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class CommonCounterServlet<T extends CommonEntity> extends CommonApplicationServlet<T> {
    protected CommonCrudRepository<T> entityRepository;
    private final Class<T> runtimeEntityClass;

    public CommonCounterServlet(CommonEntityFilterMapper<T> entityFilterMapper, Class<T> runtimeEntityClass) {
        super(entityFilterMapper);
        this.runtimeEntityClass = runtimeEntityClass;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        entityRepository = new CommonCrudRepository<>(runtimeEntityClass);
    }

    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        long result = entityRepository.countByFilter(getFilters(request));
        out.print(result);
    }
}
