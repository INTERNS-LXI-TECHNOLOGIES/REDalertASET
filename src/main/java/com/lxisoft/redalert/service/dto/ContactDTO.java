package com.lxisoft.redalert.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.Relation;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private Relation relation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if (contactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
