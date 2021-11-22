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
public class OrganizationDto  extends CommonDto{
    @NotNull
    public String fullName;
    @Min(0)
    public int annualTurnover;
}
