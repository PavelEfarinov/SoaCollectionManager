package itmo.efarinov.soa.dto;

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
public class CoordinatesDto extends CommonDto {
    @Min(-485)
    @NotNull
    public Integer x;
    public int y;
}
