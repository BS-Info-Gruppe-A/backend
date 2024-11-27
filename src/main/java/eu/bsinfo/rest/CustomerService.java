package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.rest.objects.Customers;
import eu.bsinfo.rest.objects.UpdatableCustomer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
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
    public Response upsertCustomer(ICustomer customer) throws SQLException {
        if (Backend.getInstance().getCustomerRepository().findById(customer.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).entity("ID already in use").build();
        }
        Backend.getInstance().getCustomerRepository().insert(customer);

        return Response.status(Response.Status.CREATED).entity(customer).build();
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
            currentCustomer.setFirstName(customer.lastName());
        }

        Backend.getInstance().getCustomerRepository().update(currentCustomer);

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ICustomer getById(@PathParam("userId") UUID userId) throws SQLException {
        var customer = Backend.getInstance().getCustomerRepository().findById(userId);

        if (customer == null) {
            throw new NotFoundException();
        } else {
            return customer;
        }
    }
}
