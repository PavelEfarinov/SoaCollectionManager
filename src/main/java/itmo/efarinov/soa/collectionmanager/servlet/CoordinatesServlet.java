package itmo.efarinov.soa.collectionmanager.servlet;

import itmo.efarinov.soa.collectionmanager.dto.CoordinatesDto;
import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
import itmo.efarinov.soa.collectionmanager.filter.CoordinatesFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/coordinates/*")
public class CoordinatesServlet extends CommonApplicationServlet<CoordinatesEntity, CoordinatesDto> {
    public CoordinatesServlet() {
        super(new CoordinatesFilterMapper(), CoordinatesEntity.class, CoordinatesDto.class);
    }
}
