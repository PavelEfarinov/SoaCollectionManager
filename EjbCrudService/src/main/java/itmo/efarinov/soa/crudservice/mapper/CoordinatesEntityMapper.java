package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;

@Stateless
@LocalBean
public class CoordinatesEntityMapper implements CommonEntityMapper<CoordinatesEntity, CoordinatesDto> {
    @Override
    public CoordinatesEntity toModel(CoordinatesDto dto) {
        return CoordinatesEntity.builder().x(dto.x).y(dto.y).build();
    }
}
