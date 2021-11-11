package itmo.efarinov.soa.collectionmanager.servlet.crud;

import itmo.efarinov.soa.collectionmanager.dto.OrganizationDto;
import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import itmo.efarinov.soa.collectionmanager.filter.OrganizationsFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/organizations/*")
public class OrganizationServlet extends CommonCrudServlet<OrganizationEntity, OrganizationDto> {

    public OrganizationServlet() {
        super(new OrganizationsFilterMapper(), OrganizationEntity.class, OrganizationDto.class);
    }
}
