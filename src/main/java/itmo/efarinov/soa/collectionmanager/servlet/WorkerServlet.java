package itmo.efarinov.soa.collectionmanager.servlet;

import itmo.efarinov.soa.collectionmanager.dto.WorkerDto;
import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import itmo.efarinov.soa.collectionmanager.filter.WorkerFilterMapper;
import itmo.efarinov.soa.collectionmanager.repository.CommonCrudRepository;
import itmo.efarinov.soa.collectionmanager.utils.gson.GsonObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/workers/*")
public class WorkerServlet extends CommonApplicationServlet<WorkerEntity, WorkerDto> {
    private CommonCrudRepository<OrganizationEntity> organizationRepository;
    private CommonCrudRepository<CoordinatesEntity> coordinatesRepository;

    public WorkerServlet() {
        super(new WorkerFilterMapper(), WorkerEntity.class, WorkerDto.class);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        organizationRepository = new CommonCrudRepository<>(OrganizationEntity.class);
        coordinatesRepository = new CommonCrudRepository<>(CoordinatesEntity.class);
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        WorkerDto postArgs = GsonObjectMapper.Mapper.fromJson(request.getReader(), WorkerDto.class);
        WorkerEntity worker = postArgs.toModel();
        if (postArgs.getOrganizationId() != null) {
            worker.setOrganization(organizationRepository.getById(postArgs.getOrganizationId()));
        }
        worker.setCoordinates(coordinatesRepository.getById(postArgs.getCoordinatesId()));
        entityRepository.save(worker);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(worker.toJson());
        }
    }

    @SneakyThrows
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        int id = getIdFromLastPathPart(request);
        WorkerDto putArgs = GsonObjectMapper.Mapper.fromJson(request.getReader(), WorkerDto.class);
        WorkerEntity worker = putArgs.toModel();
        worker.setId(id);
        worker.setOrganization(organizationRepository.getById(putArgs.getOrganizationId()));
        worker.setCoordinates(coordinatesRepository.getById(putArgs.getCoordinatesId()));
        entityRepository.update(worker);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(worker.toJson());
        }
    }

    public void destroy() {
        organizationRepository.close();
        coordinatesRepository.close();
    }
}