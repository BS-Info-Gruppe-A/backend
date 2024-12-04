package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.bsinfo.Backend;
import eu.bsinfo.entity.DefaultReading;
import eu.bsinfo.entity.IReading;
import eu.bsinfo.rest.objects.Reading;
import eu.bsinfo.rest.objects.Readings;
import eu.bsinfo.rest.objects.UpdatableReading;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReadingServiceTest extends AbstractRestTest {

    private static UUID testId;

    @BeforeAll
    public static void beforeAll() {
        testId = UUID.randomUUID();
    }

    @Order(1)
    @Test
    public void testReadingGetAll() throws JsonProcessingException {
        var output = get(target("/readings"), Readings.class, "Readings");
        Assertions.assertEquals(3, output.readings().size());
    }

    @Order(2)
    @Test
    public void testReadingByCustomer() throws JsonProcessingException {
        var target = target("/readings")
                .queryParam("customer", "0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5");
        var output = get(target, Readings.class, "Readings");
        Assertions.assertEquals(3, output.readings().size());
    }

    @Order(3)
    @Test
    public void testReadingByKind() throws JsonProcessingException {
        var target = target("/readings")
                .queryParam("kindOfMeter", "HEIZUNG");
        var output = get(target, Readings.class, "Readings");
        Assertions.assertEquals(1, output.readings().size());
    }


    @Order(4)
    @Test
    public void testReadingByDateRange() throws JsonProcessingException {
        var target = target("/readings")
                .queryParam("start", "2023-11-02")
                .queryParam("end", "2023-11-03");
        var output = get(target, Readings.class, "Readings");
        Assertions.assertEquals(2, output.readings().size());
    }

    @Order(5)
    @Test
    public void testGetById() throws JsonProcessingException {
        var id = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        var output = get(target("/readings").path(id.toString()), Reading.class, "Reading");

        Assertions.assertEquals(output.reading().getId(), id);
    }

    @Order(6)
    @Test
    public void testUpdate() throws SQLException {
        var id = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        var current = getBackend().getReadingRepository().findById(id);
        var update = new UpdatableReading(id, LocalDate.now(), "afefe", 12, false, 10.0, IReading.KindOfMeter.HEIZUNG);

        target("/readings").request().put(Entity.json(update), Void.class);

        var updated = getBackend().getReadingRepository().findById(id);

        Assertions.assertNotEquals(current, updated);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(update.dateOfReading(), updated.getDateOfReading());
        Assertions.assertEquals(update.comment(), updated.getComment());
        Assertions.assertEquals(update.meterId(), updated.getMeterId());
        Assertions.assertEquals(update.substitute(), updated.getSubstitute());
        Assertions.assertEquals(update.meterCount(), updated.getMeterCount());
        Assertions.assertEquals(update.kindOfMeter(), updated.getKindOfMeter());
    }

    @Order(7)
    @Test
    public void testCreate() throws SQLException, JsonProcessingException {
        var customer = Backend.getInstance().getCustomerRepository().getAll().getFirst();
        var newReading = new DefaultReading(
                testId,
                "Apple ist nicht gut",
                customer,
                LocalDate.now(),
                IReading.KindOfMeter.HEIZUNG,
                13.37,
                1,
                false
        );

        var reading =
                post(target("/readings"), Entity.json(newReading), Reading.class, "Reading")
                        .reading();

        var insertedReading = getBackend().getReadingRepository().findById(reading.getId());

        Assertions.assertEquals(reading, insertedReading);
    }

    @Test
    @Order(8)
    public void testIDCannotBeDuplicated() throws SQLException {
        var customer = Backend.getInstance().getCustomerRepository().getAll().getFirst();
        var newReading = new DefaultReading(
                testId,
                "Apple ist nicht gut",
                customer,
                LocalDate.now(),
                IReading.KindOfMeter.HEIZUNG,
                13.37,
                1,
                false
        );

        try (var response = target("/readings").request().post(Entity.json(newReading), Response.class)) {
            Assertions.assertEquals(Response.Status.CONFLICT, response.getStatusInfo().toEnum());
        }
    }

    @Order(9)
    @Test
    public void testDelete() throws SQLException, JsonProcessingException {
        delete(target("/readings").path(testId.toString()), Reading.class, "Reading");

        var deletedCustomer = getBackend().getCustomerRepository().findById(testId);

        Assertions.assertNull(deletedCustomer);
    }

    @Order(7)
    @Test
    public void testInvalidIdOnGet() {
        try (var response = target("/readings").path("92dad020-b9ac-4e6b-9b15-a12128ce2bce").request().get(Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }

    @Order(7)
    @Test
    public void testInvalidIdOnPut() {
        var update = new UpdatableReading(UUID.fromString("92dad020-b9ac-4e6b-9b15-a12128ce2bce"), null, null, null, null, null, null);
        try (var response = target("/readings").request().put(Entity.json(update), Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }


    @Order(8)
    @Test
    public void testInvalidIdOnDelete() {
        try (var response = target("/readings").path("92dad020-b9ac-4e6b-9b15-a12128ce2bce").request().delete(Response.class)) {
            Assertions.assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo().toEnum());
        }
    }

    @Order(9)
    @Test
    public void testCreateWithoutId() throws SQLException, JsonProcessingException {        var customer = Backend.getInstance().getCustomerRepository().getAll().getFirst();
        var newReading = new DefaultReading(
                null,
                "Apple ist nicht gut",
                customer,
                LocalDate.now(),
                IReading.KindOfMeter.HEIZUNG,
                13.37,
                1,
                false
        );

        var reading =
                post(target("/readings"), Entity.json(newReading), Reading.class, "Reading")
                        .reading();

        var insertedReading = getBackend().getReadingRepository().findById(reading.getId());

        Assertions.assertEquals(reading, insertedReading);
    }
}
