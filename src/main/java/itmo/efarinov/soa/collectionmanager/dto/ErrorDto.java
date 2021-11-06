package itmo.efarinov.soa.collectionmanager.dto;

import itmo.efarinov.soa.collectionmanager.utils.JsonableModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto extends JsonableModel {
    private String Error;
    private String Message;
}
