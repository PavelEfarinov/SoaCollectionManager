package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crud.entity.CoordinatesEntity;
import itmo.efarinov.soa.crud.interfaces.mapper.ICoordinatesMapper;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class CoordinatesEntityMapper implements ICoordinatesMapper {
    @Override
    public CoordinatesEntity toModel(CoordinatesDto dto) {
        return CoordinatesEntity.builder().x(dto.x).y(dto.y).build();
    }
}
