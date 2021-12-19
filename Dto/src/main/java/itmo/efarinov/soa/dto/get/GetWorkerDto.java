package itmo.efarinov.soa.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import itmo.efarinov.soa.dto.PositionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetWorkerDto {
    public Integer id;
    public String name; //Поле не может быть null, Строка не может быть пустой
    public GetCoordinatesDto coordinates; //Поле не может быть null

    public java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    public float salary; //Значение поля должно быть больше 0
    public java.time.LocalDateTime startDate; //Поле не может быть null
    public java.time.LocalDateTime endDate; //Поле может быть null
    public PositionDto position; //Поле не может быть null
    public GetOrganizationDto organization; //Поле может быть null
}


