package itmo.efarinov.soa.collectionmanager.entity;

import itmo.efarinov.soa.collectionmanager.utils.JsonableModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class CommonEntity extends JsonableModel {
    @Id
    @GeneratedValue
    protected Integer id;
}
