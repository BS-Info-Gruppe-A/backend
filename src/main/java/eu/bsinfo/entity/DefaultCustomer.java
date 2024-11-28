package eu.bsinfo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DefaultCustomer implements ICustomer {

    @JsonProperty(required = true)
    private UUID id;
    @JsonProperty(required = true)
    private LocalDate birthDate;
    @JsonProperty(required = true)
    private String firstName;
    @JsonProperty(required = true)
    private Gender gender;
    @JsonProperty(required = true)
    private String lastName;

    @JsonCreator
    public DefaultCustomer(@JsonProperty("id") UUID id, @JsonProperty("birthDate") LocalDate birthDate, @JsonProperty("firstName") String firstName, @JsonProperty("gender") Gender gender, @JsonProperty("lastName") String lastName) {
        this.id = id;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultCustomer that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(birthDate, that.birthDate) && Objects.equals(firstName, that.firstName) && gender == that.gender && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate, firstName, gender, lastName);
    }


    @Override
    public String toString() {
        return "DefaultCustomer{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
