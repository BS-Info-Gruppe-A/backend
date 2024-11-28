package eu.bsinfo.rest.objects;

import eu.bsinfo.entity.ICustomer;

import java.util.List;

public record Customers(List<ICustomer> customers) {
}
