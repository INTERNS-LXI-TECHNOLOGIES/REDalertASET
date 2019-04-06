package com.lxisoft.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.AlertType;

/**
 * A DTO for the Alert entity.
 */
public class AlertDTO implements Serializable {

    private Long id;

    private AlertType type;


    private Long userDomainId;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public Long getUserDomainId() {
        return userDomainId;
    }

    public void setUserDomainId(Long userDomainId) {
        this.userDomainId = userDomainId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlertDTO alertDTO = (AlertDTO) o;
        if (alertDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alertDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlertDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", userDomain=" + getUserDomainId() +
            ", location=" + getLocationId() +
            "}";
    }
}
