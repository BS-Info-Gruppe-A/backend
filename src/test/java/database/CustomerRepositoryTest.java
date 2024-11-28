package database;

import eu.bsinfo.database.repository.CustomerRepository;
import eu.bsinfo.database.repository.Repository;
import eu.bsinfo.entity.DefaultCustomer;
import eu.bsinfo.entity.ICustomer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerRepositoryTest extends AbstractRepositoryTest<ICustomer> {
    public static @NotNull DefaultCustomer getDefaultCustomer(UUID id) {
        return new DefaultCustomer(id, LocalDate.now(), "Marc", ICustomer.Gender.D, "Degner");
    }

    @Override
    protected Repository<ICustomer> getRepository() {
        return new CustomerRepository(getDatabaseManager());
    }

    @Override
    protected ICustomer newEntity(UUID id) {
        return getDefaultCustomer(id);
    }

    @Override
    protected void mutateEntity(@NotNull ICustomer entity) {
        entity.setFirstName("Marc2");
    }

    @Override
    protected int getSeedCount() {
        return 4;
    }
}
