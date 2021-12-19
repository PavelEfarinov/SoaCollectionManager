package itmo.efarinov.soa.dto;

import itmo.efarinov.soa.json.JsonableModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto extends JsonableModel {
    public String error;
    public String message;
}
