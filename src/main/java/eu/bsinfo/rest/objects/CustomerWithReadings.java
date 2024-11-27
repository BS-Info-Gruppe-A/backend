package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerWithReadings(
        UUID id,
        String firstName,
        String lastName,
        ICustomer.Gender gender,
        LocalDate birthDate,
        List<Reading> readings
) {
    public CustomerWithReadings(ICustomer customer, List<IReading> readings) {
        var mappedReadings = readings.stream().map(Reading::new).toList();
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getGender(), customer.getBirthDate(), mappedReadings);
    }

    public record Reading(
            UUID id,
            String comment,
            LocalDate dateOfReading,
            IReading.KindOfMeter kindOfMeter,
            double meterCount,
            int meterId,
            boolean substitute
    ) {
        public Reading(IReading reading) {
            this(reading.getId(), reading.getComment(), reading.getDateOfReading(), reading.getKindOfMeter(), reading.getMeterCount(), reading.getMeterId(), reading.getSubstitute());
        }

    }
}
