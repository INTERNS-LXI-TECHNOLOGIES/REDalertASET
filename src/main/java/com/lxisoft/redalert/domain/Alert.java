package com.lxisoft.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.AlertType;

/**
 * A Alert.
 */
@Entity
@Table(name = "alert")
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private AlertType type;

    @ManyToOne
    @JsonIgnoreProperties("alerts")
    private UserDomain userDomain;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "alert")
    private Set<EmergencyService> emergencyServices = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public Alert type(AlertType type) {
        this.type = type;
        return this;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public Alert userDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
        return this;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    public Location getLocation() {
        return location;
    }

    public Alert location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<EmergencyService> getEmergencyServices() {
        return emergencyServices;
    }

    public Alert emergencyServices(Set<EmergencyService> emergencyServices) {
        this.emergencyServices = emergencyServices;
        return this;
    }

    public Alert addEmergencyServices(EmergencyService emergencyService) {
        this.emergencyServices.add(emergencyService);
        emergencyService.setAlert(this);
        return this;
    }

    public Alert removeEmergencyServices(EmergencyService emergencyService) {
        this.emergencyServices.remove(emergencyService);
        emergencyService.setAlert(null);
        return this;
    }

    public void setEmergencyServices(Set<EmergencyService> emergencyServices) {
        this.emergencyServices = emergencyServices;
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
        Alert alert = (Alert) o;
        if (alert.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alert.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Alert{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
