package com.lxisoft.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A UserDomain.
 */
@Entity
@Table(name = "user_domain")
public class UserDomain implements Serializable {


    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "locality")
    private String locality;

    @Column(name = "mobile")
    private Long mobile;
    
    
	@Column(nullable = false)
	private Boolean activated = true;

    public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	@OneToMany(mappedBy = "userDomain")
    private Set<Alert> alerts = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "user_domain_contacts",
               joinColumns = @JoinColumn(name = "user_domain_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "contacts_id", referencedColumnName = "id"))
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_domain_roles",
               joinColumns = @JoinColumn(name = "user_domain_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDomain firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDomain lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserDomain email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserDomain password(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
        return this;
    }

    public void setPassword(String password) {
        this.password =new BCryptPasswordEncoder().encode(password);
    }

    public String getLocality() {
        return locality;
    }

    public UserDomain locality(String locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Long getMobile() {
        return mobile;
    }

    public UserDomain mobile(Long mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public UserDomain alerts(Set<Alert> alerts) {
        this.alerts = alerts;
        return this;
    }

    public UserDomain addAlerts(Alert alert) {
        this.alerts.add(alert);
        alert.setUserDomain(this);
        return this;
    }

    public UserDomain removeAlerts(Alert alert) {
        this.alerts.remove(alert);
        alert.setUserDomain(null);
        return this;
    }

    public void setAlerts(Set<Alert> alerts) {
        this.alerts = alerts;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public UserDomain contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public UserDomain addContacts(Contact contact) {
        this.contacts.add(contact);
        return this;
    }

    public UserDomain removeContacts(Contact contact) {
        this.contacts.remove(contact);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserDomain roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserDomain addRoles(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public UserDomain removeRoles(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        UserDomain userDomain = (UserDomain) o;
        if (userDomain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDomain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDomain{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", locality='" + getLocality() + "'" +
            ", mobile=" + getMobile() +
            "}";
    }
}
