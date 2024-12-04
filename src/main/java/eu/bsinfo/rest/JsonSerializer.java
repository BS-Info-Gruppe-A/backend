package eu.bsinfo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.time.format.DateTimeFormatter;

/// Provider for a [org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JsonMapperConfigurator]
/// using an [ObjectMapper] with a custom date format.
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class JsonSerializer extends JacksonJaxbJsonProvider {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public JsonSerializer() {
        var mapper = new ObjectMapper();
        mapper.findAndRegisterModules()
                .registerModule(new SimpleModule().addSerializer(new LocalDateSerializer()));
        super(mapper, DEFAULT_ANNOTATIONS);
    }

    private static class LocalDateSerializer extends com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer {
        public LocalDateSerializer() {
            super(FORMAT);
        }
    }
}
