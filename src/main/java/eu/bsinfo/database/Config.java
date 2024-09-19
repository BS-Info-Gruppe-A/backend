package eu.bsinfo.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public record Config(String url, String username, String password) {

    public static Config fromDefault() {
        var systemUser = System.getProperty("user.name");
        var systemHome = System.getProperty("user.home");

        return fromFile(Path.of(systemHome, "bsinfo-projekt", "database.properties"), systemUser);
    }

    public static Config fromFile(Path path, String systemUser) {
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

        } catch (IOException e) {
            throw new IllegalStateException("Could not read database.properties, please refer to README", e);
        }
    }
}
