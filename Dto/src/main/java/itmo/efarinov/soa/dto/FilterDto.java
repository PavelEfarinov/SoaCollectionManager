package itmo.efarinov.soa.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterDto extends CommonDto {
    public String fieldName;
    public String predicateType;
    public String fieldValue;
}
