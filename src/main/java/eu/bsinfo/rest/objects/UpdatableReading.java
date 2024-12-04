package eu.bsinfo.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/// Request object for updating a reading.
///
///all parameters are required only comment is optional
///
/// @param customer the new customer of the reading to update
 /// @param dateOfReading the new dateOfReading of the reading
 /// @param meterId the new meterId of the reading
 /// @param substitute the new substitute of the reading
 /// @param metercount the new metercount of the reading
 /// @param kindOfMeter the new kindOfMeter of the reading
 /// @param comment the new comment of the reading
///
public record UpdatableReading(
    @NotNull
    @JsonProperty(required = true)
    UUID id,
    @JsonProperty(required = true)
    UpdatableCustomer  customer,
    @JsonProperty(required = true)
    LocalDate dateOfReading,
    String comment,
    @JsonProperty(required = true)
    int meterId,
    @JsonProperty(required = true)
    Boolean substitute,
    @JsonProperty(required = true)
    Double metercount,
    @JsonProperty(required = true)
    IReading.KindOfMeter kindOfMeter
) {
    /// @throws NullPointerException if any param is null
    public UpdatableReading {
        Objects.requireNonNull(id, "id can not be null");
        Objects.requireNonNull(customer, "customer must can be null");
        Objects.requireNonNull(dateOfReading, "dateOfReading must can be null");
        Objects.requireNonNull(meterId, "meterId can not be null");
        Objects.requireNonNull(substitute, "substitute can not be null");
        Objects.requireNonNull(metercount, "metercount can not be null");
        Objects.requireNonNull(kindOfMeter, "kindOfMeter can not be null");
    }
}
