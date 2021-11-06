package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.entity.CoordinatesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDto extends CommonEntityDto<CoordinatesEntity> {
    private Integer x;
    private int y;

    public CoordinatesEntity toModel() {
        return CoordinatesEntity.builder().x(x).y(y).build();
    }
}
