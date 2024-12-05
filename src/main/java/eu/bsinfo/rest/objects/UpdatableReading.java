package eu.bsinfo.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/// Request object for updating a reading.
///
/// all parameters are required only comment is optional
///
/// @param dateOfReading the new dateOfReading of the reading
/// @param meterId       the new meterId of the reading
/// @param substitute    the new substitute of the reading
/// @param meterCount    the new metercount of the reading
/// @param kindOfMeter   the new kindOfMeter of the reading
/// @param comment       the new comment of the reading
public record UpdatableReading(
        @NotNull
        @JsonProperty(required = true)
        UUID id,
        @Nullable
        LocalDate dateOfReading,
        @Nullable
        String comment,
        @Nullable
        Integer meterId,
        @Nullable
        Boolean substitute,
        @Nullable
        @JsonProperty("metercount")
        Double meterCount,
        @Nullable
        IReading.KindOfMeter kindOfMeter
) {
    /// @throws NullPointerException if any param is null
    public UpdatableReading {
        Objects.requireNonNull(id, "id can not be null");
    }
}
