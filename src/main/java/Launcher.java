import eu.bsinfo.Backend;
import eu.bsinfo.database.Config;
import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.IReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

void main() throws IOException, InterruptedException {
    var databaseConfig = Config.fromDefault();
    var databaseManager = new DatabaseManager(databaseConfig.url(), databaseConfig.username(), databaseConfig.password());
    var backend = new Backend(databaseManager);
    backend.getServer().start();

    // Block the main thread until receiving SIGTERM (Ctrl+C)
    Thread.currentThread().join();
}
