package eu.bsinfo;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.CustomerRepository;
import eu.bsinfo.database.repository.ReadingRepository;
import eu.bsinfo.rest.JsonSerializer;
import jakarta.ws.rs.core.Application;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

/// Central entry point for the HTTP service.
public class Backend {
    private static Backend instance;
    private final DatabaseManager databaseManager;
    private final HttpServer server;
    private final CustomerRepository customerRepository;
    private final ReadingRepository readingRepository;
    private final ResourceConfig application = new ResourceConfig()
            .packages("eu.bsinfo.rest")
            // For some reason auto registration does not register this
            .register(JsonSerializer.class)
            // Disable automatic json processing, this is handled by eu.bsinfo.rest.JsonSerializer
            .property(CommonProperties.JSON_PROCESSING_FEATURE_DISABLE, true)
            .property(CommonProperties.USE_VIRTUAL_THREADS, true);

    public Backend(@NotNull DatabaseManager databaseManager) {
        if (instance != null) {
            throw new IllegalStateException("Backend is already initialized");
        }
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");

        this.databaseManager = databaseManager;
        this.customerRepository = new CustomerRepository(databaseManager);
        this.readingRepository = new ReadingRepository(databaseManager);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), application, false);

        instance = this;
    }

    /// Retrieves the current instance of [Backend].
    public static Backend getInstance() {
        return instance;
    }

    /// Retrieves the [DatabaseManager] used.
    @NotNull
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /// Retrieves the [CustomerRepository].
    @NotNull
    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    ///  Retrieves the [ReadingRepository].
    @NotNull
    public ReadingRepository getReadingRepository() {
        return readingRepository;
    }

    /// Retrieves the [HttpServer]
    @NotNull
    public HttpServer getServer() {
        return server;
    }

    /// Retrieves the [Application].
    @NotNull
    public Application getApplication() {
        return application;
    }

    public void close() {
        instance = null;
        server.shutdownNow();
    }
}
