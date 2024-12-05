package eu.bsinfo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(as = DefaultCustomer.class)
public interface ICustomer extends IId {

    /// Creates a new [ICustomer] from a [ResultSet].
    ///
    /// @param resultSet the [ResultSet] containing the data
    /// @return the newly created customer
    /// @throws SQLException if an SQL error occurs
    /// @see DefaultCustomer#DefaultCustomer(UUID, LocalDate, String, Gender, String)
    /// @see ICustomer#from(ResultSet)
    static ICustomer from(ResultSet resultSet) throws SQLException {
        return from(resultSet, "id");
    }

    /// Creates a new [ICustomer] from a [ResultSet].
    ///
    /// @param resultSet the [ResultSet] containing the data
    /// @param idField   the name of the id column in the result set
    /// @return the newly created customer
    /// @throws SQLException if an SQL error occurs
    /// @see DefaultCustomer#DefaultCustomer(UUID, LocalDate, String, Gender, String)
    /// @see ICustomer#from(ResultSet, String)
    static ICustomer from(ResultSet resultSet, String idField) throws SQLException {
        var dbId = resultSet.getObject(idField, UUID.class);
        var birthDate = resultSet.getTimestamp("birth_date");
        var firstName = resultSet.getString("first_name");
        var gender = resultSet.getString("gender");
        var lastName = resultSet.getString("last_name");

        return new DefaultCustomer(
                dbId,
                birthDate.toLocalDateTime().toLocalDate(),
                firstName,
                ICustomer.Gender.valueOf(gender),
                lastName
        );
    }

    LocalDate getBirthDate();

    void setBirthDate(LocalDate birtDate);

    String getFirstName();

    void setFirstName(String firstName);

    Gender getGender();

    void setGender(Gender gender);

    String getLastName();

    void setLastName(String lastName);

    enum Gender {
        D, // divers
        M, // männlich
        U, // unbekannt
        W // weiblich
    }
}
