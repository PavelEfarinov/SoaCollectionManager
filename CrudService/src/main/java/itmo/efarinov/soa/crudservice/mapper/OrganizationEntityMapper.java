package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.dto.OrganizationDto;

public class OrganizationEntityMapper extends CommonEntityMapper<OrganizationEntity, OrganizationDto>{
    @Override
    public OrganizationEntity toModel(OrganizationDto dto) {
        return OrganizationEntity
                .builder()
                .annualTurnover(dto.annualTurnover)
                .fullName(dto.fullName)
                .build();
    }
}
