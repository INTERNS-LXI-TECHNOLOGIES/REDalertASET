package com.lxisoft.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.Relation;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation")
    private Relation relation;

    @ManyToMany(mappedBy = "contacts")
    @JsonIgnore
    private Set<UserDomain> users = new HashSet<>();

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

    public Contact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Contact phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Relation getRelation() {
        return relation;
    }

    public Contact relation(Relation relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Set<UserDomain> getUsers() {
        return users;
    }

    public Contact users(Set<UserDomain> userDomains) {
        this.users = userDomains;
        return this;
    }

    public Contact addUsers(UserDomain userDomain) {
        this.users.add(userDomain);
        userDomain.getContacts().add(this);
        return this;
    }

    public Contact removeUsers(UserDomain userDomain) {
        this.users.remove(userDomain);
        userDomain.getContacts().remove(this);
        return this;
    }

    public void setUsers(Set<UserDomain> userDomains) {
        this.users = userDomains;
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
