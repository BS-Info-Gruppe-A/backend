package eu.bsinfo.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/// Implementation of [ExceptionMapper] returning `400` on [JsonProcessingExceptions][JsonProcessingException].
@Provider
public class JsonErrorHandler implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException exception) {
        String errorMessage = exception.getMessage();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
