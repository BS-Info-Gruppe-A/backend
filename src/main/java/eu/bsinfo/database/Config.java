package eu.bsinfo.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/// Configuration used by [DatabaseManager].
///
/// @param url      the JDBC compatible url to connect to, refer to the
///                                            [PostgreSQL documentation](https://jdbc.postgresql.org/documentation/use/#connecting-to-the-database)
///                                            if you are not sure what this is
/// @param username the username for authentication
/// @param password the password for authentication
public record Config(@NotNull String url, @Nullable String username, @Nullable String password) {

    public Config {
        Objects.requireNonNull(url, "url cannot be null");
    }

    /// Reads the config from the environment or its default location, which is `~/bsinfo-projekt/database.properties`.
    ///
    /// @see this#fromFile(Path, String)
    /// @see this#fromEnvironment()
    /// @return the parsed config.
    @NotNull
    public static Config fromDefault() throws IOException {
        try {
            return fromEnvironment();
        } catch (IllegalStateException ignored) {
            var systemUser = System.getProperty("user.name");
            var systemHome = System.getProperty("user.home");

            return fromFile(Path.of(systemHome, "bsinfo-projekt", "database.properties"), systemUser);
        }
    }

    /// Creates a config from the `DB_URL` environment variable
    ///
    /// @throws IllegalStateException if the `DB_URL` variable is not set
    public static Config fromEnvironment() {
        var url = System.getenv("DB_URL");
        if (url == null) {
            throw new IllegalStateException("Environment config is not set");
        }

        return new Config(url, null, null);
    }

    /// This creates a Config from a file.
    ///
    /// @param path       the [Path] to look for
    /// @param systemUser the user to connect to
    /// @return the parsed config
    @NotNull
    public static Config fromFile(@NotNull Path path, @NotNull String systemUser) throws IOException {
        Objects.requireNonNull(path, "path cannot be null");
        Objects.requireNonNull(systemUser, "systemUser cannot be null");

        try (var inputStream = Files.newInputStream(path)) {
            var properties = new Properties();
            properties.load(inputStream);

            var url = properties.getProperty(systemUser + ".db.url");
            var username = properties.getProperty(systemUser + ".db.user");
            var password = properties.getProperty(systemUser + ".db.password");

            Objects.requireNonNull(url, "Please specify db.url in database.properties");
            Objects.requireNonNull(username, "Please specify db.username in database.properties");
            Objects.requireNonNull(password, "Please specify db.password in database.properties");

            return new Config(url, username, password);
        }
    }
}
