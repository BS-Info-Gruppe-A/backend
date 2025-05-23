package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.InputFormat;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaLocation;
import com.networknt.schema.SpecVersion;
import database.TestDatabaseUtil;
import eu.bsinfo.Backend;
import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.rest.JsonSerializer;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.SyncInvoker;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/// Abstract test for REST services.
///
/// @see JerseyTest
/// @see JerseyTest#target()
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractRestTest extends JerseyTest {

    private static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
    private static final JsonSerializer serializer = new JsonSerializer();
    @Container
    protected static PostgreSQLContainer<?> psql = TestDatabaseUtil.createTestDatabase();
    private static Backend backend;

    @BeforeAll
    public static void setupBackend() {
        var databaseManager = new DatabaseManager(psql.getJdbcUrl(), psql.getUsername(), psql.getPassword(), 1);
        backend = new Backend(databaseManager);
    }

    @AfterAll
    public static void closeBackend() {
        backend.close();
    }

    @Override
    protected Application configure() {
        return backend.getApplication();
    }

    /// Returns the [Backend] instance to test.
    protected final Backend getBackend() {
        return backend;
    }

    protected <T> T get(WebTarget target, Class<T> responseType, String name) throws JsonProcessingException {
        return validateResponse(SyncInvoker::get, target, responseType, name);
    }

    protected <T> T delete(WebTarget target, Class<T> responseType, String name) throws JsonProcessingException {
        return validateResponse(SyncInvoker::delete, target, responseType, name);
    }

    protected <T> T put(WebTarget target, Entity<?> entity, Class<T> responseType, String name) throws JsonProcessingException {
        return validateResponseWithBody(SyncInvoker::put, target, entity, responseType, name);
    }

    protected <T> T post(WebTarget target, Entity<?> entity, Class<T> responseType, String name) throws JsonProcessingException {
        return validateResponseWithBody(SyncInvoker::post, target, entity, responseType, name);
    }

    private <T> T validateResponse(RequestFunction requester, WebTarget target, Class<T> responseType, String name) throws JsonProcessingException {
        try (var response = requester.request(target.request(), Response.class)) {
            var json = response.readEntity(String.class);
            if (name != null) {
                var schema = schemaFactory.getSchema(SchemaLocation.of("classpath:JSON_Schema_%s.json".formatted(name)));
                var errors = schema.validate(json, InputFormat.JSON, context -> context.getExecutionConfig().setFormatAssertionsEnabled(true));
                Assertions.assertSame(0, errors.size(), () -> "Got json validation errors: " + errors);
            }

            var mapper = serializer.getContext(responseType);

            return mapper.readValue(json, responseType);
        }
    }

    private <T> T validateResponseWithBody(RequestFunctionWithBody requester, WebTarget target, Entity<?> entity, Class<T> responseType, String name) throws JsonProcessingException {
        try (var response = requester.request(target.request(), entity, Response.class)) {
            var json = response.readEntity(String.class);
            if (name != null) {
                var schema = schemaFactory.getSchema(SchemaLocation.of("classpath:JSON_Schema_%s.json".formatted(name)));
                var errors = schema.validate(json, InputFormat.JSON, context -> context.getExecutionConfig().setFormatAssertionsEnabled(true));
                Assertions.assertSame(0, errors.size(), () -> "Got json validation errors: " + errors);
            }

            var mapper = serializer.getContext(responseType);

            return mapper.readValue(json, responseType);
        }
    }

    interface RequestFunction {
        <T> T request(Invocation.Builder invocationBuilder, Class<T> clazz);
    }

    interface RequestFunctionWithBody {
        <T> T request(Invocation.Builder invocationBuilder, Entity<?> entity, Class<T> clazz);
    }
}
