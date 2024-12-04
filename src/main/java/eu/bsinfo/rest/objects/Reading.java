package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

/// Response for [ICustomer].
public record Reading(@NotNull IReading reading) {
}
