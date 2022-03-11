package itmo.efarinov.soa.crud.interfaces.mapper;

import itmo.efarinov.soa.crud.entity.CoordinatesEntity;
import itmo.efarinov.soa.dto.CoordinatesDto;
import jakarta.ejb.Remote;

@Remote
public interface ICoordinatesMapper extends ICommonEntityMapper<CoordinatesEntity, CoordinatesDto> {
}
