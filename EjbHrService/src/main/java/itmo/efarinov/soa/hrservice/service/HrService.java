package itmo.efarinov.soa.hrservice.service;


import itmo.efarinov.soa.dto.WorkerDto;
import itmo.efarinov.soa.dto.get.GetOrganizationDto;
import itmo.efarinov.soa.dto.get.GetWorkerDto;
import itmo.efarinov.soa.hrservice.facade.exceptions.NestedRequestException;
import itmo.efarinov.soa.hrservice.interfaces.IHrService;
import itmo.efarinov.soa.hrservice.interfaces.IOrganizationFacade;
import itmo.efarinov.soa.hrservice.interfaces.IWorkerFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.SneakyThrows;

@Stateless
public class HrService implements IHrService {
    @EJB
    private IWorkerFacade workerFacade;
    @EJB
    private IOrganizationFacade orgFacade;

    @SneakyThrows
    @Override
    public void moveEmployee(int workerId, int oldOrgId, int newOrgId) {
        GetWorkerDto worker = workerFacade.getById(workerId);

        if (worker.organization.id != oldOrgId) {
            throw new NestedRequestException("Worker with id " + workerId + " has different organization");
        }

        GetOrganizationDto oldOrg = orgFacade.getById(oldOrgId);
        GetOrganizationDto newOrg = orgFacade.getById(newOrgId);

        WorkerDto withNewSalary = WorkerDto
                .builder()
                .coordinatesId(worker.coordinates.id)
                .organizationId(newOrg.id)
                .salary(worker.salary)
                .endDate(worker.endDate)
                .name(worker.name)
                .position(worker.position)
                .startDate(worker.startDate)
                .build();

        workerFacade.updateById(worker.id, withNewSalary);
    }

    @SneakyThrows
    @Override
    public double indexSalary(int workerId, float indexCoefficient) {
        GetWorkerDto worker = workerFacade.getById(workerId);

        WorkerDto withNewSalary = WorkerDto
                .builder()
                .coordinatesId(worker.coordinates.id)
                .organizationId(worker.organization.id)
                .salary(worker.salary * indexCoefficient)
                .endDate(worker.endDate)
                .name(worker.name)
                .position(worker.position)
                .startDate(worker.startDate)
                .build();

        return workerFacade.updateById(worker.id, withNewSalary).salary;
    }
}
