package itmo.efarinov.soa.collectionmanager.servlet.counter;

import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import itmo.efarinov.soa.collectionmanager.filter.WorkerFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/workers/count")
public class WorkerCounterServlet extends CommonCounterServlet<WorkerEntity> {
    public WorkerCounterServlet() {
        super(new WorkerFilterMapper(), WorkerEntity.class);
    }
}
