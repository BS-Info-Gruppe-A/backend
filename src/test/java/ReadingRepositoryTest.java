import eu.bsinfo.database.repository.Repository;
import eu.bsinfo.database.repository.ReadingRepository;
import eu.bsinfo.entity.DefaultReading;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ReadingRepositoryTest extends AbstractRepositoryTest<IReading> {
    @Override
    protected Repository<IReading> getRepository() {
        return new ReadingRepository(getDatabaseManager());
    }

    @Override
    protected IReading newEntity(UUID id) {
        var customer = CustomerRepositoryTest.getDefaultCustomer(UUID.randomUUID());

        return new DefaultReading(" testComment", customer,  LocalDate.now(), IReading.KindOfMeter.valueOf("WASSER"), 67.00, "testId", true);
    }

    @Override
    protected void mutateEntity(@NotNull IReading entity) {
        entity.setComment("test2Comment");
    }
}

