package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crud.entity.OrganizationEntity;
import itmo.efarinov.soa.crud.interfaces.mapper.IOrganizationMapper;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
public class OrganizationEntityMapper implements IOrganizationMapper {
    @Override
    public OrganizationEntity toModel(OrganizationDto dto) {
        return OrganizationEntity
                .builder()
                .annualTurnover(dto.annualTurnover)
                .fullName(dto.fullName)
                .build();
    }
}
