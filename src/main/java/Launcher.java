import eu.bsinfo.Backend;
import eu.bsinfo.database.Config;
import eu.bsinfo.database.DatabaseManager;

void main() {
    var databaseConfig = Config.fromDefault();
    var databaseManager = new DatabaseManager(databaseConfig.url(), databaseConfig.username(), databaseConfig.password());

    new Backend(databaseManager);
}
