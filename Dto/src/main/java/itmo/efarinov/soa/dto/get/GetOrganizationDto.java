package itmo.efarinov.soa.dto.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrganizationDto {
    public Integer id;
    public String fullName;
    public int annualTurnover;
}
