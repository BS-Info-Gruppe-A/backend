package eu.bsinfo.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.entity.ICustomer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/// Request object for updating a customer.
///
/// all parameters but `id` are optional
///
/// @param id the id of the customer to update
/// @param firstName the new first name of the customer
/// @param lastName the new last name of the customer
/// @param gender the new gender of the customer
public record UpdatableCustomer(
        @NotNull @JsonProperty(required = true) UUID id,
        @Nullable String firstName,
        @Nullable String lastName,
        @Nullable ICustomer.Gender gender
) {
}
