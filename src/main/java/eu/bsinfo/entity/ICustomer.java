package eu.bsinfo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public interface ICustomer extends IId {

   enum Gender {
      D, // divers
      M, // männlich
      U, // unbekannt
      W // weiblich
   }

   LocalDate getBirthDate();

   String getFirstName();

   Gender getGender();

   String getLastName();

   void setBirthDate(LocalDate birtDate);

   void setFirstName(String firstName);

   void setGender(Gender gender);

   void setLastName(String lastName);

   /**
    * Creates a new {@link ICustomer} from a {@link ResultSet}
    * @param resultSet the {@link ResultSet} containing the data
    * @return the newly created customer
    * @throws SQLException if an SQL error occurs
    * @see DefaultCustomer#DefaultCustomer(UUID, LocalDate, String, Gender, String)
    */
   static ICustomer from(ResultSet resultSet) throws SQLException {
      var dbId = resultSet.getObject("id", UUID.class);
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
}
