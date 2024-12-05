package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/// Response for [IReading].
public record Readings(@NotNull List<IReading> readings) {
    /// @throws NullPointerException if customers is null
    public Readings {
        Objects.requireNonNull(readings, "readings must not be null");
    }
}
