package itmo.efarinov.soa.crud.entity;

import itmo.efarinov.soa.dto.CommonDto;
import itmo.efarinov.soa.json.JsonableModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class CommonEntity<T extends CommonDto> extends JsonableModel implements Serializable {
    @Id
    @GeneratedValue
    protected Integer id;
}
