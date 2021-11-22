package itmo.efarinov.soa.dto;

import itmo.efarinov.soa.json.JsonableModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto extends JsonableModel {
    public String Error;
    public String Message;
}
