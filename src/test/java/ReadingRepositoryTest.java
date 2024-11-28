import eu.bsinfo.database.repository.CustomerRepository;
import eu.bsinfo.database.repository.Repository;
import eu.bsinfo.database.repository.ReadingRepository;
import eu.bsinfo.entity.DefaultReading;
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
    protected IReading newEntity(UUID id) {
        var customer = CustomerRepositoryTest.getDefaultCustomer(UUID.fromString("0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5"));

        return new DefaultReading(id, " testComment", customer,  LocalDate.now(), IReading.KindOfMeter.valueOf("WASSER"), 67.00, 1337, true);
    }

    @Override
    protected void mutateEntity(@NotNull IReading entity) {
        entity.setComment("test2Comment");
    }
    @Test
    @Order(4)
    public void testGetReadings() throws SQLException {
        var id = UUID.fromString("f0d02fd0-01a7-49d1-b4e2-dab9e9cafe76");
        var foundEntity = getRepository().findById(id);
        Assertions.assertNotNull(getRepository().getReadings(null, null, IReading.KindOfMeter.WASSER), "Entity not found");
    }

}

