package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// Response object containing a customer object with their readings.
///
/// @param customer the [Customer]
/// @param readings the [List] of [Readings][Reading]
public record CustomerWithReadings(
        @NotNull ICustomer customer,
        @NotNull List<Reading> readings
) {

    /// @throws NullPointerException if any parameer is null
    public CustomerWithReadings {
        Objects.requireNonNull(customer, "customer must not be null");
        Objects.requireNonNull(readings, "readings must not be null");
    }

    /// Constructor for creating a [CustomerWithReadings] from a [ICustomer]
    ///
    /// @param readings the readings of the customer
    /// @throws NullPointerException if any parameer is null
    public static CustomerWithReadings from(@NotNull ICustomer customer, @NotNull List<IReading> readings) {
        Objects.requireNonNull(customer, "customer must not be null");
        Objects.requireNonNull(readings, "readings must not be null");

        var mappedReadings = readings.stream().map(Reading::new).toList();
        return new CustomerWithReadings(customer, mappedReadings);
    }

    /// Response object of a reading without its customer.
    ///
    /// @param id            the id of the reading
    /// @param comment       the comment of the reading
    /// @param dateOfReading the [LocalDate] on which the reading was recorded
    /// @param kindOfMeter   the [kind][eu.bsinfo.entity.IReading.KindOfMeter] of the meter
    /// @param meterCount    the current value of the meter
    /// @param meterId       the id of the meter
    /// @param substitute    whether the reading is a substitute
    public record Reading(
            @NotNull UUID id,
            @NotNull String comment,
            @NotNull LocalDate dateOfReading,
            @NotNull IReading.KindOfMeter kindOfMeter,
            double meterCount,
            int meterId,
            boolean substitute
    ) {
        public Reading {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(comment, "comment must not be null");
            Objects.requireNonNull(dateOfReading, "dateOfReading must not be null");
            Objects.requireNonNull(kindOfMeter, "kindOfMeter must not be null");
        }

        /// Creates a [Reading] from an [IReading].
        public Reading(@NotNull IReading reading) {
            Objects.requireNonNull(reading, "reading must not be null");

            this(reading.getId(), reading.getComment(), reading.getDateOfReading(), reading.getKindOfMeter(), reading.getMeterCount(), reading.getMeterId(), reading.getSubstitute());
        }

    }
}
