package itmo.efarinov.soa.hr.interfaces;

import jakarta.ejb.Remote;

@Remote
public interface IHrService {
    void moveEmployee(
            int workerId,
            int oldOrgId,
            int newOrgId) throws Exception;

    double indexSalary(int workerId,
                       float indexCoefficient) throws Exception;
}
