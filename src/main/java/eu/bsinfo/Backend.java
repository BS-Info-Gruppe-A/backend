package eu.bsinfo;

import eu.bsinfo.database.DatabaseManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Backend {
    private final DatabaseManager databaseManager;

    public Backend(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        this.databaseManager = databaseManager;
    }


    @NotNull
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
