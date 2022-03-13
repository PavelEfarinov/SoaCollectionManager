package itmo.efarinov.soa.crud.entity;

import itmo.efarinov.soa.dto.OrganizationDto;
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

@Entity(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationEntity extends CommonEntity<OrganizationDto> implements Serializable {
    @NotNull
    private String fullName;
    @Min(0)
    private int annualTurnover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
