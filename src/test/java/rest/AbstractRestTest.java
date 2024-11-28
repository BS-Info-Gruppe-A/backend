package rest;

import database.TestDatabaseUtil;
import eu.bsinfo.Backend;
import eu.bsinfo.database.DatabaseManager;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractRestTest extends JerseyTest {

    @SuppressWarnings("FieldCanBeLocal")
    private Backend backend;

    @Container
    protected static PostgreSQLContainer<?> psql = TestDatabaseUtil.createTestDatabase();

    @Override
    protected Application configure() {
        var databaseManager = new DatabaseManager(psql.getJdbcUrl(), psql.getUsername(), psql.getPassword(), 1);
        backend = new Backend(databaseManager);
        return backend.getApplication();
    }

    protected Backend getBackend() {
        return backend;
    }
}
