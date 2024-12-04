package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import eu.bsinfo.entity.IReading;
import eu.bsinfo.rest.objects.Reading;
import eu.bsinfo.rest.objects.ReadingList;
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
import java.util.UUID;

@Path("/readings")
public class ReadingService {

    private static final Logger log = new SimpleLoggerFactory().getLogger("ReadingService");

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReading(IReading reading) throws SQLException {
        if (Backend.getInstance().getReadingRepository().findById(reading.getId()) != null) {
            log.error("ID already in use");
            return Response.status(Response.Status.CONFLICT).entity("ID already in use").build();
        }
        Backend.getInstance().getReadingRepository().insert(reading);

        return Response.created(URI.create("readings/" + reading.getId())).entity(reading).build();
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
    public ReadingList getReadingsByFilter(@QueryParam("start") LocalDate startDate, @QueryParam("end") LocalDate endDate, @QueryParam("kindOfMeter") IReading.KindOfMeter kindOfMeter, @QueryParam("customer") UUID userId) {

        List<IReading> findReadingByFilters;
        try {
            findReadingByFilters = Backend.getInstance().getReadingRepository().getReadings(startDate, endDate, kindOfMeter, userId);
        } catch (SQLException e) {
            log.error("failed to get Readings");
            throw new RuntimeException(e);
        }
        if (findReadingByFilters == null) {
            log.error("cant find any reading with filters: startDate: [{}],endDate: [{}], kindOfMeter: [{}], userId: [{}]", startDate, endDate, kindOfMeter, userId);
            throw new NotFoundException();
        }
        return new ReadingList(findReadingByFilters);
    }

}
