package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.entity.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto extends CommonEntityDto<OrganizationEntity>{
    @NotNull
    private String fullName;
    @Min(0)
    private int annualTurnover;

    public OrganizationEntity toModel() {
        return OrganizationEntity
                .builder()
                .annualTurnover(annualTurnover)
                .fullName(fullName)
                .build();
    }
}
