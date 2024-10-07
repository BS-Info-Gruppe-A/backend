import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.Repository;
import eu.bsinfo.entity.IId;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.UUID;

public abstract class AbstractRepositoryTest<T extends IId> {
    @SuppressWarnings("resource") // closed by afterAll()
    protected static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:latest")
            .withInitScripts("schema.sql", "seed.sql");

    protected abstract Repository<T> getRepository();
    protected abstract T newEntity(UUID id);
    protected abstract void mutateEntity(@NotNull T entity);

    protected DatabaseManager getDatabaseManager() {
        return new DatabaseManager(psql.getJdbcUrl(), psql.getUsername(), psql.getPassword());
    }

    @BeforeAll
    public static void beforeAll() {
        psql.start();
    }

    @AfterAll
    public static void afterAll() {
        psql.stop();
    }

    @Test
    @Order(1)
    public void testInsert() throws SQLException {
        var id = UUID.randomUUID();
        Assertions.assertTrue(getRepository().insert(newEntity(id)), "Entity insert failed");
        Assertions.assertNotNull(getRepository().findById(id), "Entity not inserted");
    }

    @Test
    @Order(2)
    public void testFind() throws SQLException {
        var id = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        Assertions.assertNotNull(getRepository().findById(id), "Entity not found");
    }

    @Test
    @Order(3)
    public void testUpdate() throws SQLException {
        var id = UUID.fromString("d7726d0e-a42e-4f5a-8be9-e80358f9dd37");
        T entity = getRepository().findById(id);
        T currentState = getRepository().findById(id);

        Assertions.assertNotNull(entity, "Entity not found");
        mutateEntity(entity);
        Assertions.assertTrue(getRepository().update(entity), "Entity update failed");
        var updatedEntity = getRepository().findById(id);

        Assertions.assertEquals(entity, updatedEntity, "Entity not updated");
        Assertions.assertNotEquals(updatedEntity, currentState, "Entity not updated");
    }

    @Test
    @Order(4)
    public void testDelete() throws SQLException {
        var id = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        Assertions.assertTrue(getRepository().delete(id), "Entity delete failed");
        Assertions.assertNull(getRepository().findById(id), "Entity not deleted");
    }
}
