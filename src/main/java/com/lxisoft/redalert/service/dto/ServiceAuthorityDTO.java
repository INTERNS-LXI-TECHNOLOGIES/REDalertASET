package com.lxisoft.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ServiceAuthority entity.
 */
public class ServiceAuthorityDTO implements Serializable {

    private Long id;

    private String districtName;

    private String authorityName;

    private Long phone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceAuthorityDTO serviceAuthorityDTO = (ServiceAuthorityDTO) o;
        if (serviceAuthorityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceAuthorityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceAuthorityDTO{" +
            "id=" + getId() +
            ", districtName='" + getDistrictName() + "'" +
            ", authorityName='" + getAuthorityName() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
