package database;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.Repository;
import eu.bsinfo.entity.IId;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.UUID;

/// Abstract unit test for implementations of the [Repository] interface.
///
/// @param <T> the type of the repositories interface
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractRepositoryTest<T extends IId> {

    /// [PostgreSQLContainer] used for communicating with the database.
    @Container
    protected static PostgreSQLContainer<?> psql = TestDatabaseUtil.createTestDatabase();

    /// Creates a new [Repository] which should be tested.
    ///
    /// @see AbstractRepositoryTest#getDatabaseManager()
    protected abstract Repository<T> getRepository();

    /// Creates a new entity.
    ///
    /// @param id the desired id of the created entity
    @NotNull
    protected abstract T newEntity(@NotNull UUID id);

    /// Changes the provided entity.
    ///
    /// This is used to test the update endpoint and can change anything about the entity, but the id
    ///
    /// @param entity the entity to change
    protected abstract void mutateEntity(@NotNull T entity);

    /// Returns the amount of items in the seed file.
    protected abstract int getSeedCount();

    /// Creates a new [DatabaseManager] using the [AbstractRepositoryTest#psql] container.
    protected final DatabaseManager getDatabaseManager() {
        return new DatabaseManager(psql.getJdbcUrl(), psql.getUsername(), psql.getPassword(), 1);
    }

    @Test
    @Order(1)
    public void testInsert() throws SQLException {
        var id = UUID.randomUUID();
        Assertions.assertTrue(getRepository().save(newEntity(id)), "Entity insert failed");
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
        Assertions.assertTrue(getRepository().save(entity), "Entity update failed");
        var updatedEntity = getRepository().findById(id);

        Assertions.assertEquals(entity, updatedEntity, "Entity not updated");
        Assertions.assertNotEquals(updatedEntity, currentState, "Entity not updated");
    }

    @Test
    @Order(3)
    public void testGetAll() throws SQLException {
        var found = getRepository().getAll();

        // +1 since testInsert() inserts a new one
        Assertions.assertSame(getSeedCount() + 1, found.size(), "Found invalid amount of entities");
    }

    @Test
    public void testInvalidIdReturnsNull() throws SQLException {
        var found = getRepository().findById(UUID.randomUUID());

        Assertions.assertNull(found, "Entity not found");
    }

    @Test
    @Order(Integer.MAX_VALUE) // Use max_value here so we can't schedule any test after this by accident
    public void testDelete() throws SQLException {
        var id = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        var foundEntity = getRepository().findById(id);
        Assertions.assertNotNull(foundEntity, "Entity not found");
        Assertions.assertTrue(getRepository().delete(foundEntity), "Entity delete failed");
        Assertions.assertNull(getRepository().findById(id), "Entity not deleted");
    }
}
