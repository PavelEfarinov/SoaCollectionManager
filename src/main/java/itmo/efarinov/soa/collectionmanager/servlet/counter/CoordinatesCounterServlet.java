package itmo.efarinov.soa.collectionmanager.servlet.counter;

import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
import itmo.efarinov.soa.collectionmanager.filter.CoordinatesFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/coordinates/count")
public class CoordinatesCounterServlet extends CommonCounterServlet<CoordinatesEntity> {
    public CoordinatesCounterServlet() {
        super(new CoordinatesFilterMapper(), CoordinatesEntity.class);
    }
}