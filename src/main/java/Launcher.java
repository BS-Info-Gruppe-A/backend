import eu.bsinfo.Backend;
import eu.bsinfo.database.Config;
import eu.bsinfo.database.DatabaseManager;

import java.io.IOException;

void main() throws IOException {
    var databaseConfig = Config.fromDefault();
    var databaseManager = new DatabaseManager(databaseConfig.url(), databaseConfig.username(), databaseConfig.password());

    new Backend(databaseManager);
}
