package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;

import java.util.List;

/// Response object for a list of customers.
/// @param customers the list of customers
public record Customers(List<ICustomer> customers) {
}
