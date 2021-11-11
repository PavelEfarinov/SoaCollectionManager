package itmo.efarinov.soa.collectionmanager.servlet;

import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import itmo.efarinov.soa.collectionmanager.filter.WorkerFilterMapper;
import itmo.efarinov.soa.collectionmanager.repository.WorkerRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/workers/salary/sum")
public class WorkerSalaryServlet extends CommonApplicationServlet<WorkerEntity> {
    protected WorkerRepository workerRepository = new WorkerRepository();

    public WorkerSalaryServlet() {
        super(new WorkerFilterMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            out.println(workerRepository.countSalarySum());
        }
    }
}
