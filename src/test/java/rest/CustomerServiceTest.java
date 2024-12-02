package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.bsinfo.entity.DefaultCustomer;
import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.rest.objects.Customer;
import eu.bsinfo.rest.objects.CustomerWithReadings;
import eu.bsinfo.rest.objects.Customers;
import eu.bsinfo.rest.objects.UpdatableCustomer;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class CustomerServiceTest extends AbstractRestTest {

    private static final UUID testId = UUID.randomUUID();

    @Order(1)
    @Test
    public void testGet() throws JsonProcessingException {
        var output = get(target("/customers"), Customers.class, "Customers");
        Assertions.assertEquals(4, output.customers().size());
    }

    @Order(2)
    @Test
    public void testGetById() throws JsonProcessingException {
        var id = UUID.fromString("d7726d0e-a42e-4f5a-8be9-e80358f9dd37");
        var output = get(target("/customers").path(id.toString()), Customer.class, "Customer");

        Assertions.assertEquals(output.customer().getId(), id);
    }

    @Order(3)
    @Test
    public void testUpdate() throws SQLException {
        var id = UUID.fromString("d7726d0e-a42e-4f5a-8be9-e80358f9dd37");
        var current = getBackend().getCustomerRepository().findById(id);
        var update = new UpdatableCustomer(id, "Bernhard", "EEchle", ICustomer.Gender.D);

        target("/customers").request().put(Entity.json(update), Void.class);

        var updated = getBackend().getCustomerRepository().findById(id);

        Assertions.assertNotEquals(current, updated);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(updated.getFirstName(), update.firstName());
        Assertions.assertEquals(updated.getLastName(), update.lastName());
        Assertions.assertEquals(updated.getGender(), update.gender());
    }
    @Order(4)
    @Test
    public void testCreate() throws SQLException, JsonProcessingException {
        var newCustomer = new DefaultCustomer(testId, LocalDate.now(), "Marc", ICustomer.Gender.D, "Degner");

        var customer =
                post(target("/customers"), Entity.json(newCustomer), Customer.class, "Customer")
                        .customer();

        var insertedCustomer = getBackend().getCustomerRepository().findById(customer.getId());

        Assertions.assertEquals(customer, insertedCustomer);
    }

    @Test
    @Order(5)
    public void testIDCannotBeDuplicated() {
        var newCustomer = new DefaultCustomer(testId, LocalDate.now(), "Marc", ICustomer.Gender.D, "Degner");

        try (var response = target("/customers").request().post(Entity.json(newCustomer), Response.class)) {
            Assertions.assertEquals(Response.Status.CONFLICT, response.getStatusInfo().toEnum());
        }
    }

    @Order(6)
    @Test
    public void testDelete() throws SQLException, JsonProcessingException {
        var id = UUID.fromString("0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5");
        var response = delete(target("/customers").path(id.toString()), CustomerWithReadings.class, "CustomerWithReadings");

        var deletedCustomer = getBackend().getCustomerRepository().findById(id);

        Assertions.assertNull(deletedCustomer);
        Assertions.assertEquals(id, response.customer().getId());

        var readingIds = response.readings().stream().map(CustomerWithReadings.Reading::id).toList();
        var id1 = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        var id2 = UUID.fromString("d7726d0e-a42e-4f5a-8be9-e80358f9dd37");
        var id3 = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");

        Assertions.assertTrue(readingIds.contains(id1));
        Assertions.assertTrue(readingIds.contains(id2));
        Assertions.assertTrue(readingIds.contains(id3));
    }

    @Order(7)
    @Test
    public void testInvalidIdOnGet() {
        try (var response = target("/customers").path("92dad020-b9ac-4e6b-9b15-a12128ce2bce").request().get(Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }

    @Order(7)
    @Test
    public void testInvalidIdOnPut() {
        var update = new UpdatableCustomer(UUID.fromString("92dad020-b9ac-4e6b-9b15-a12128ce2bce"), null, null, null);
        try (var response = target("/customers").request().put(Entity.json(update), Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }


    @Order(8)
    @Test
    public void testInvalidIdOnDelete() {
        try (var response = target("/customers").path("92dad020-b9ac-4e6b-9b15-a12128ce2bce").request().delete(Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }

    @Order(9)
    @Test
    public void testCreateWithoutId() throws SQLException, JsonProcessingException {
        var newCustomer = new DefaultCustomer(null, LocalDate.now(), "Marc", ICustomer.Gender.D, "Degner");

        var customer =
                post(target("/customers"), Entity.json(newCustomer), Customer.class, "Customer")
                        .customer();

        var insertedCustomer = getBackend().getCustomerRepository().findById(customer.getId());

        Assertions.assertEquals(customer, insertedCustomer);
    }
}
