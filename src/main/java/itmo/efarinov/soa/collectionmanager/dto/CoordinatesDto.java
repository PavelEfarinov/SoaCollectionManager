package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
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
public class CoordinatesDto extends CommonEntityDto<CoordinatesEntity> {
    @Min(-485)
    @NotNull
    private Integer x;
    private int y;

    public CoordinatesEntity toModel() {
        return CoordinatesEntity.builder().x(x).y(y).build();
    }
}
