package eu.bsinfo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.text.SimpleDateFormat;

/// Provider for a [org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JsonMapperConfigurator]
/// using an [ObjectMapper] with a custom date format.
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class JsonSerializer extends JacksonJaxbJsonProvider {
    public JsonSerializer() {
        var mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.registerModule(new JavaTimeModule());
        super(mapper, DEFAULT_ANNOTATIONS);
    }
}
