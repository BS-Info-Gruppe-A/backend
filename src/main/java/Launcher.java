import eu.bsinfo.Backend;
import eu.bsinfo.database.Config;
import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.database.repository.CustomerRepository;
import eu.bsinfo.entity.IReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

private static final Logger logger = LoggerFactory.getLogger(IReading.class);

void main() throws IOException {
    var databaseConfig = Config.fromDefault();
    var databaseManager = new DatabaseManager(databaseConfig.url(), databaseConfig.username(), databaseConfig.password());
    new Backend(databaseManager);
    CustomerRepository repo = new CustomerRepository(databaseManager);
    try {
        var test = repo.getAll();
        logger.info("result was {}",test);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
