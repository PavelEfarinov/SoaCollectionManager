package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.Position;
import itmo.efarinov.soa.crudservice.entity.WorkerEntity;
import itmo.efarinov.soa.crudservice.interfaces.mapper.IWorkerMapper;
import itmo.efarinov.soa.dto.WorkerDto;
import jakarta.ejb.Stateless;

@Stateless
public class WorkerEntityMapper implements IWorkerMapper {
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
