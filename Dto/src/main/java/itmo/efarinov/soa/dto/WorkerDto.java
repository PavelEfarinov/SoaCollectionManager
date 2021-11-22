package itmo.efarinov.soa.dto;

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
public class WorkerDto extends CommonDto{
    @NotNull
    @NotBlank
    public String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    public int coordinatesId; //Поле не может быть null
    @Min(0)
    public float salary; //Значение поля должно быть больше 0
    @NotNull
    public java.time.LocalDateTime startDate; //Поле не может быть null
    public java.time.LocalDateTime endDate; //Поле может быть null
    public PositionDto position; //Поле не может быть null
    public Integer organizationId; //Поле может быть null
}
