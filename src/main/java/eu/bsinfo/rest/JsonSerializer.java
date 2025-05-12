package eu.bsinfo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.time.format.DateTimeFormatter;

/// Provider for a [org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JsonMapperConfigurator]
/// using an [ObjectMapper] with a custom date format.
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class JsonSerializer implements ContextResolver<ObjectMapper> {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ObjectMapper mapper;

    public JsonSerializer() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules()
                .registerModule(new SimpleModule().addSerializer(new LocalDateSerializer(FORMAT)));
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
