package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.entity.Position;
import itmo.efarinov.soa.collectionmanager.entity.WorkerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto extends CommonEntityDto<WorkerEntity>{
    @NotNull
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private int coordinatesId; //Поле не может быть null
    @Min(0)
    private float salary; //Значение поля должно быть больше 0
    @NotNull
    private java.time.LocalDateTime startDate; //Поле не может быть null
    private java.time.ZonedDateTime endDate; //Поле может быть null
    @NotNull
    private Position position; //Поле не может быть null
    private Integer organizationId; //Поле может быть null

    public WorkerEntity toModel()
    {
        return WorkerEntity.builder()
                .name(name)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .position(position)
                .build();
    }
}
