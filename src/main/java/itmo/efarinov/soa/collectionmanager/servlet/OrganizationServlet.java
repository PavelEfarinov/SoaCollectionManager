package itmo.efarinov.soa.collectionmanager.servlet;

import itmo.efarinov.soa.collectionmanager.dto.OrganizationDto;
import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import itmo.efarinov.soa.collectionmanager.filter.OrganizationsFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/organizations/*")
public class OrganizationServlet extends CommonApplicationServlet<OrganizationEntity, OrganizationDto> {

    public OrganizationServlet() {
        super(new OrganizationsFilterMapper(), OrganizationEntity.class, OrganizationDto.class);
    }
}
