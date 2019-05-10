package com.lxisoft.redalert.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceAuthority.
 */
@Entity
@Table(name = "service_authority")
public class ServiceAuthority implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "authority_name")
    private String authorityName;

    @Column(name = "phone")
    private Long phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public ServiceAuthority districtName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public ServiceAuthority authorityName(String authorityName) {
        this.authorityName = authorityName;
        return this;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Long getPhone() {
        return phone;
    }

    public ServiceAuthority phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceAuthority serviceAuthority = (ServiceAuthority) o;
        if (serviceAuthority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceAuthority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceAuthority{" +
            "id=" + getId() +
            ", districtName='" + getDistrictName() + "'" +
            ", authorityName='" + getAuthorityName() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
