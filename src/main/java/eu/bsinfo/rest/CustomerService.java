package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;
import eu.bsinfo.rest.objects.Customer;
import eu.bsinfo.rest.objects.CustomerWithReadings;
import eu.bsinfo.rest.objects.Customers;
import eu.bsinfo.rest.objects.UpdatableCustomer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("/customers")
public class CustomerService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Customers getAll() throws SQLException {
        return new Customers(Backend.getInstance().getCustomerRepository().getAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(ICustomer customer) throws SQLException {
        var id = customer.getId();
        if (id != null &&Backend.getInstance().getCustomerRepository().findById(id) != null) {
            return Response.status(Response.Status.CONFLICT).entity("ID already in use").build();
        }
        if (id == null) {
            customer.setId(UUID.randomUUID());
        }
        Backend.getInstance().getCustomerRepository().insert(customer);

        return Response.created(URI.create("customers/"+ customer.getId())).entity(new Customer(customer)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(UpdatableCustomer customer) throws SQLException {
        var currentCustomer = Backend.getInstance().getCustomerRepository().findById(customer.id());
        if (currentCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }

        if (customer.gender() != null) {
            currentCustomer.setGender(customer.gender());
        }
        if (customer.firstName() != null) {
            currentCustomer.setFirstName(customer.firstName());
        }
        if (customer.lastName() != null) {
            currentCustomer.setLastName(customer.lastName());
        }

        Backend.getInstance().getCustomerRepository().update(currentCustomer);

        return Response.accepted().build();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getById(@PathParam("userId") UUID userId) throws SQLException {
        var customer = Backend.getInstance().getCustomerRepository().findById(userId);

        if (customer == null) {
            throw new NotFoundException();
        } else {
            return new Customer(customer);
        }
    }

    @Path("/{userId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerWithReadings deleteById(@PathParam("userId") UUID userId) throws SQLException {
        var customerToDelete = Backend.getInstance().getCustomerRepository().findById(userId);
        if (customerToDelete == null) {
            throw new NotFoundException();
        }

        List<IReading> readings = Backend.getInstance().getReadingRepository().getReadings(null, null, null, userId);

        Backend.getInstance().getCustomerRepository().delete(customerToDelete);
        return CustomerWithReadings.from(customerToDelete, readings);
    }
}
