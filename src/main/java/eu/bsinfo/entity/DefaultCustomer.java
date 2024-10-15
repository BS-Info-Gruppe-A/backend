package eu.bsinfo.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DefaultCustomer implements ICustomer {

    private UUID id;
    private LocalDate birthDate;
    private String firstName;
    private Gender gender;
    private String lastName;

    public DefaultCustomer(UUID id, LocalDate birthDate, String firstName, Gender gender, String lastName) {
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
    public String getFirstName() {
        return firstName;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
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
}
