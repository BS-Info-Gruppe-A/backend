package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/// Response object for a list of customers.
///
/// @param customers the list of customers
public record Customers(@NotNull List<ICustomer> customers) {
    /// @throws NullPointerException if customers is null
    public Customers {
        Objects.requireNonNull(customers, "customers must not be null");
    }
}
