package itmo.efarinov.soa.collectionmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerEntity extends CommonEntity {
    @NotNull
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private CoordinatesEntity coordinates; //Поле не может быть null
    @NotNull
    @CreationTimestamp
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Min(0)
    private float salary; //Значение поля должно быть больше 0
    @NotNull
    private java.time.LocalDateTime startDate; //Поле не может быть null
    private java.time.ZonedDateTime endDate; //Поле может быть null
    @NotNull
    @Enumerated(EnumType.STRING)
    private Position position; //Поле не может быть null
    @ManyToOne(fetch = FetchType.EAGER)
    private OrganizationEntity organization; //Поле может быть null

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkerEntity that = (WorkerEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


