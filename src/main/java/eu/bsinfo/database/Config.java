package eu.bsinfo.database;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * Configuration used by {@link DatabaseManager}.
 * @param url the JDBC compatible url to connect to, refer to the
 *            <a href="https://jdbc.postgresql.org/documentation/use/#connecting-to-the-database>PostgreSQL documentation</a>
 *            if you are not sure what this is
 * @param username the username for authentication
 * @param password the password for authentication
 */
public record Config(@NotNull String url, @NotNull String username, @NotNull String password) {

    public Config {
        Objects.requireNonNull(url, "url cannot be null");
        Objects.requireNonNull(username, "username cannot be null");
        Objects.requireNonNull(password, "password cannot be null");
    }

    /**
     * Reads the config from its default location, which is {@code ~/bsinfo-projekt/database.properties}.
     * @return the parsed config.
     */
    @NotNull
    public static Config fromDefault() throws IOException {
        var systemUser = System.getProperty("user.name");
        var systemHome = System.getProperty("user.home");

        return fromFile(Path.of(systemHome, "bsinfo-projekt", "database.properties"), systemUser);
    }

    /**
     * This creates a Config from a file.
     * @param path the {@link Path} to look for
     * @param systemUser the user to connect to
     * @return the parsed config
     */
    @NotNull
    public static Config fromFile(@NotNull Path path, @NotNull String systemUser) throws IOException {
        Objects.requireNonNull(path, "path cannot be null");
        Objects.requireNonNull(systemUser, "systemUser cannot be null");

        try(var inputStream = Files.newInputStream(path)) {
            var properties = new Properties();
            properties.load(inputStream);

            var url = properties.getProperty(STR."\{systemUser}.db.url");
            var username = properties.getProperty(STR."\{systemUser}.db.user");
            var password = properties.getProperty(STR."\{systemUser}.db.password");

            Objects.requireNonNull(url, "Please specify db.url in database.properties");
            Objects.requireNonNull(username, "Please specify db.username in database.properties");
            Objects.requireNonNull(password, "Please specify db.password in database.properties");

            return new Config(url, username, password);
        }
    }
}
