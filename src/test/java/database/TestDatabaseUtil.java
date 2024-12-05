package database;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestDatabaseUtil {
    @SuppressWarnings("resource") // handled by JUNIT
    public static PostgreSQLContainer<?> createTestDatabase() {
        return new PostgreSQLContainer<>("postgres:latest")
                .withInitScripts("schema.sql", "seed.sql");
    }
}
