package itmo.efarinov.soa.crud.entity;

import itmo.efarinov.soa.dto.CoordinatesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "coordinates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatesEntity extends CommonEntity<CoordinatesDto> implements Serializable {
    @Min(-485)
    @NotNull
    private int x;
    private int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CoordinatesEntity that = (CoordinatesEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
