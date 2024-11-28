package eu.bsinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.CustomerRepository;
import eu.bsinfo.database.repository.ReadingRepository;
import jakarta.ws.rs.core.Application;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

public class Backend {
    private static Backend instance;
    private final DatabaseManager databaseManager;
    private final HttpServer server;
    private final CustomerRepository customerRepository;
    private final ReadingRepository readingRepository;
    private final ResourceConfig application = new ResourceConfig().packages("eu.bsinfo.rest")
            .property(CommonProperties.USE_VIRTUAL_THREADS, true);

    public Backend(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        this.databaseManager = databaseManager;
        this.customerRepository = new CustomerRepository(databaseManager);
        this.readingRepository = new ReadingRepository(databaseManager);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080"), application, false);

        instance = this;
    }

    public static Backend getInstance() {
        return instance;
    }

    @NotNull
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public HttpServer getServer() {
        return server;
    }

    public Application getApplication() {
        return application;
    }

    public ReadingRepository getReadingRepository() {
        return readingRepository;
    }
}
