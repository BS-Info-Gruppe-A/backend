package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/// Response object containing a customer object with their readings.
///
/// @param id the id of the customer
/// @param firstName the first name of the customer
/// @param lastName the last name of the customer
/// @param gender the gender of the customer
/// @param birthDate the birthdate of the customer, as a [LocalDate]
/// @param readings the customers readings
public record CustomerWithReadings(
        UUID id,
        String firstName,
        String lastName,
        ICustomer.Gender gender,
        LocalDate birthDate,
        List<Reading> readings
) {
    /// Constructor for creating a [CustomerWithReadings] from a [ICustomer]
    /// @param readings the readings of the customer
    public CustomerWithReadings(ICustomer customer, List<IReading> readings) {
        var mappedReadings = readings.stream().map(Reading::new).toList();
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getGender(), customer.getBirthDate(), mappedReadings);
    }

    /// Response object of a reading without its customer.
    ///
    /// @param id the id of the reading
    /// @param comment the comment of the reading
    /// @param dateOfReading the [LocalDate] on which the reading was recorded
    /// @param kindOfMeter the [kind][eu.bsinfo.entity.IReading.KindOfMeter] of the meter
    /// @param meterCount the current value of the meter
    /// @param meterId the id of the meter
    /// @param substitute whether the reading is a substitute
    public record Reading(
            UUID id,
            String comment,
            LocalDate dateOfReading,
            IReading.KindOfMeter kindOfMeter,
            double meterCount,
            int meterId,
            boolean substitute
    ) {

        /// Creates a [Reading] from an [IReading].
        public Reading(IReading reading) {
            this(reading.getId(), reading.getComment(), reading.getDateOfReading(), reading.getKindOfMeter(), reading.getMeterCount(), reading.getMeterId(), reading.getSubstitute());
        }

    }
}
