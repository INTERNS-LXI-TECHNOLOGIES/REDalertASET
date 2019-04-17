package com.lxisoft.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.ServiceName;

/**
 * A EmergencyService.
 */
@Entity
@Table(name = "emergency_service")
public class EmergencyService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ServiceName name;

    @Column(name = "phone")
    private Long phone;

    @ManyToOne
    @JsonIgnoreProperties("emergencyServices")
    private Alert alert;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceName getName() {
        return name;
    }

    public EmergencyService name(ServiceName name) {
        this.name = name;
        return this;
    }

    public void setName(ServiceName name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public EmergencyService phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Alert getAlert() {
        return alert;
    }

    public EmergencyService alert(Alert alert) {
        this.alert = alert;
        return this;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
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
        EmergencyService emergencyService = (EmergencyService) o;
        if (emergencyService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emergencyService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmergencyService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
