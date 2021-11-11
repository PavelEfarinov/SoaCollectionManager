package itmo.efarinov.soa.collectionmanager.servlet.counter;

import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import itmo.efarinov.soa.collectionmanager.filter.OrganizationsFilterMapper;

import javax.servlet.annotation.WebServlet;

@WebServlet("/organizations/count")
public class OrganizationCounterServlet extends CommonCounterServlet<OrganizationEntity> {
    public OrganizationCounterServlet() {
        super(new OrganizationsFilterMapper(), OrganizationEntity.class);
    }
}