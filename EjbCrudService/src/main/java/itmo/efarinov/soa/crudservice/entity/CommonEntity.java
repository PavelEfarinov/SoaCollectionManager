package itmo.efarinov.soa.crudservice.entity;

import itmo.efarinov.soa.dto.CommonDto;
import itmo.efarinov.soa.json.JsonableModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class CommonEntity<T extends CommonDto> extends JsonableModel {
    @Id
    @GeneratedValue
    protected Integer id;
}
