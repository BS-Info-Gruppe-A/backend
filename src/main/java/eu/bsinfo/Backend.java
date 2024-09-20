package eu.bsinfo;

import eu.bsinfo.database.DatabaseManager;

public class Backend {
    private final DatabaseManager databaseManager;

    public Backend(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
