package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import eu.bsinfo.entity.IReading;
import eu.bsinfo.rest.objects.Reading;
import eu.bsinfo.rest.objects.Readings;
import eu.bsinfo.rest.objects.UpdatableReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.impl.SimpleLoggerFactory;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/readings")
public class ReadingService {

    private static final Logger log = new SimpleLoggerFactory().getLogger("ReadingService");

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReading(IReading reading) throws SQLException {
        if (reading.getId() != null && Backend.getInstance().getReadingRepository().findById(reading.getId()) != null) {
            log.error("ID already in use");
            return Response.status(Response.Status.CONFLICT).entity("ID already in use").build();
        }
        if (reading.getId() == null) {
            reading.setId(UUID.randomUUID());
        }
        Backend.getInstance().getReadingRepository().insert(reading);

        return Response.created(URI.create("readings/" + reading.getId())).entity(new Reading(reading)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReading(UpdatableReading reading) throws SQLException {
        var currentReading = Backend.getInstance().getReadingRepository().findById(reading.id());
        if (currentReading == null) {
            log.error("cannot find any reading");
            return Response.status(Response.Status.NOT_FOUND).entity("Reading not found").build();
        }

        if (reading.comment() != null) {
            currentReading.setComment(reading.comment());
        }
        if (reading.dateOfReading() != null) {
            currentReading.setDateOfReading(reading.dateOfReading());
        }
        if (reading.meterId() != null) {
            currentReading.setMeterId(reading.meterId());
        }
        if (reading.meterCount() != null) {
            currentReading.setMeterCount(reading.meterCount());
        }
        if (reading.kindOfMeter() != null) {
            currentReading.setKindOfMeter(reading.kindOfMeter());
        }
        if (reading.substitute() != null) {
            currentReading.setSubstitute(reading.substitute());
        }
        Backend.getInstance().getReadingRepository().update(currentReading);

        return Response.accepted().build();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Reading getById(@PathParam("userId") UUID userId) throws SQLException {
        var reading = Backend.getInstance().getReadingRepository().findById(userId);

        if (reading == null) {
            log.error("cannot find any reading with userId: [{}]", userId);
            throw new NotFoundException();
        } else {
            return new Reading(reading);
        }
    }

    @Path("/{userId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Reading deleteById(@PathParam("userId") UUID userId) throws SQLException {
        var readingToDelete = Backend.getInstance().getReadingRepository().findById(userId);
        if (readingToDelete == null) {
            log.error("cannot find any reading with userId: [{}]", userId);
            throw new NotFoundException();

        }

        Backend.getInstance().getReadingRepository().delete(readingToDelete);
        return new Reading(readingToDelete);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Readings getReadingsByFilter(@QueryParam("start") Optional<LocalDate> startDate, @QueryParam("end") Optional<LocalDate> endDate, @QueryParam("kindOfMeter") Optional<IReading.KindOfMeter> kindOfMeter, @QueryParam("customer") Optional<UUID> userId) throws SQLException {
        List<IReading> findReadingByFilters = Backend.getInstance().getReadingRepository().getReadings(startDate.orElse(null), endDate.orElse(null), kindOfMeter.orElse(null), userId.orElse(null));
        if (findReadingByFilters == null) {
            throw new NotFoundException();
        }
        return new Readings(findReadingByFilters);
    }

}
