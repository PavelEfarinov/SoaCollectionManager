package itmo.efarinov.soa.crudservice.mapper;

import itmo.efarinov.soa.crudservice.entity.OrganizationEntity;
import itmo.efarinov.soa.dto.OrganizationDto;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class OrganizationEntityMapper implements CommonEntityMapper<OrganizationEntity, OrganizationDto> {
    @Override
    public OrganizationEntity toModel(OrganizationDto dto) {
        return OrganizationEntity
                .builder()
                .annualTurnover(dto.annualTurnover)
                .fullName(dto.fullName)
                .build();
    }
}
