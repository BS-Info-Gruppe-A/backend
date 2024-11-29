package database;

import eu.bsinfo.database.repository.ReadingRepository;
import eu.bsinfo.entity.DefaultReading;
import eu.bsinfo.entity.IId;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class ReadingRepositoryTest extends AbstractRepositoryTest<IReading> {
    @Override
    protected ReadingRepository getRepository() {
        return new ReadingRepository(getDatabaseManager());
    }

    @Override
    protected @NotNull IReading newEntity(@NotNull UUID id) {
        var customer = CustomerRepositoryTest.getDefaultCustomer(UUID.fromString("0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5"));

        return new DefaultReading(id, "Gatschonga", customer, LocalDate.ofEpochDay(1), IReading.KindOfMeter.valueOf("WASSER"), 67.00, 1337, true);
    }

    @Override
    protected void mutateEntity(@NotNull IReading entity) {
        entity.setComment("test2Comment");
    }

    @Override
    protected int getSeedCount() {
        return 3;
    }

    @Test
    @Order(4)
    public void testGetByKindOfMeter() throws SQLException {
        var id = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        var foundEntity = getRepository().getReadings(null, null, IReading.KindOfMeter.HEIZUNG, null);

        Assertions.assertSame(1, foundEntity.size(), "Found invalid amount of entities");
        Assertions.assertEquals(id, foundEntity.getFirst().getId(), "Found invalid entity");
    }

    @Test
    @Order(5)
    public void testFilterByDateRange() throws SQLException {
        var id = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        var foundEntity = getRepository().getReadings(LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 2), null, null);

        Assertions.assertSame(1, foundEntity.size(), "Found invalid amount of entities");
        Assertions.assertEquals(id, foundEntity.getFirst().getId(), "Found invalid entity");
    }

    @Test
    @Order(6)
    public void testFilterByDateEnd() throws SQLException {
        var id = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        var foundEntity = getRepository().getReadings(null, LocalDate.of(2023, 11, 2), null, null);

        // first the the one created by testInsrt()
        Assertions.assertSame(2, foundEntity.size(), "Found invalid amount of entities");
        Assertions.assertEquals(id, foundEntity.getLast().getId(), "Found invalid entity");
    }

    @Test
    @Order(7)
    public void testFilterByDateStart() throws SQLException {
        var id = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        var foundEntity = getRepository().getReadings(LocalDate.of(2023, 11, 4), null, null, null);

        Assertions.assertSame(1, foundEntity.size(), "Found invalid amount of entities");
        Assertions.assertEquals(id, foundEntity.getFirst().getId(), "Found invalid entity");
    }

    @Test
    @Order(8)
    public void testFilterByCustomer() throws SQLException {
        var id1 = UUID.fromString("92dad020-b9ac-4e6b-9b15-a02128ce2bce");
        var id2 = UUID.fromString("d7726d0e-a42e-4f5a-8be9-e80358f9dd37");
        var id3 = UUID.fromString("f889d010-3b3d-4517-9694-df6bcc806fba");
        var foundEntity = getRepository().getReadings(null, null, null, UUID.fromString("0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5"));

        var entities = foundEntity.stream().map(IId::getId).toList();

        Assertions.assertTrue(entities.contains(id1));
        Assertions.assertTrue(entities.contains(id2));
        Assertions.assertTrue(entities.contains(id3));
    }
}
