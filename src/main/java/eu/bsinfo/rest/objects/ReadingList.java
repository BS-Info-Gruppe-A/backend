package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/// Response for [ICustomer].
public record ReadingList(@NotNull List<IReading> reading) {
}
