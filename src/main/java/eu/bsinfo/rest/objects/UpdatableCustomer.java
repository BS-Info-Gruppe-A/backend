package eu.bsinfo.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bsinfo.entity.ICustomer;

import java.util.UUID;

public record UpdatableCustomer(
        @JsonProperty(required = true) UUID id,
        String firstName,
        String lastName,
        ICustomer.Gender gender
) { }
