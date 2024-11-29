package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import org.jetbrains.annotations.NotNull;

/// Response for [ICustomer].
public record Customer(@NotNull ICustomer customer) {
}
