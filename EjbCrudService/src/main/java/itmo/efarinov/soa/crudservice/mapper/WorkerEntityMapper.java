package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.Position;
import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;

@Stateless
@LocalBean
public class WorkerEntityMapper implements CommonEntityMapper<WorkerEntity, WorkerDto> {
    @Override
    public WorkerEntity toModel(WorkerDto dto) {
        return WorkerEntity.builder()
                .name(dto.name)
                .salary(dto.salary)
                .startDate(dto.startDate)
                .endDate(dto.endDate)
                .position(Position.valueOf(dto.position.toString()))
                .build();
    }
}
