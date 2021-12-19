package itmo.efarinov.soa.dto.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCoordinatesDto {
    public Integer id;
    public int x;
    public int y;
}
