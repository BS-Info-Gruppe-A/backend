package eu.bsinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;
import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.CustomerRepository;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

public class Backend {
    private final DatabaseManager databaseManager;
    private final HttpServer server;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper= new ObjectMapper();
    private static Backend instance;

    public Backend(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        objectMapper.registerModule(new JavaTimeModule());

        this.databaseManager = databaseManager;
        this.customerRepository = new CustomerRepository(databaseManager);
        final var config = new ResourceConfig().packages("eu.bsinfo.rest");
        this.server = JdkHttpServerFactory
                .createHttpServer(URI.create("http://localhost:8081/"), config);
        instance = this;
    }

    @NotNull
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public static Backend getInstance() {
        return instance;
    }
}
