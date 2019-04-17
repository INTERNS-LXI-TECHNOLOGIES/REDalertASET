package com.lxisoft.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.ServiceName;

/**
 * A DTO for the EmergencyService entity.
 */
public class EmergencyServiceDTO implements Serializable {

    private Long id;

    private ServiceName name;

    private Long phone;


    private Long alertId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceName getName() {
        return name;
    }

    public void setName(ServiceName name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmergencyServiceDTO emergencyServiceDTO = (EmergencyServiceDTO) o;
        if (emergencyServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emergencyServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmergencyServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", alert=" + getAlertId() +
            "}";
    }
}
