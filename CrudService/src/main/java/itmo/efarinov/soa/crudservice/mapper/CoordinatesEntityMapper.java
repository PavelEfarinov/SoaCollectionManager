package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.dto.CoordinatesDto;

public class CoordinatesEntityMapper extends CommonEntityMapper<CoordinatesEntity, CoordinatesDto> {
    @Override
    public CoordinatesEntity toModel(CoordinatesDto dto) {
        return CoordinatesEntity.builder().x(dto.x).y(dto.y).build();
    }
}
