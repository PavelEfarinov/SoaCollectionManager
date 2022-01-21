package itmo.efarinov.soa.crudservice.interfaces.mapper;

import itmo.efarinov.soa.crudservice.entity.CoordinatesEntity;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesMapper extends ICommonEntityMapper<CoordinatesEntity, CoordinatesDto> {
}
