import eu.bsinfo.database.Config;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.resource.Shared;
import org.junitpioneer.jupiter.resource.TemporaryDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigTest {

    private static final String CONFIG_NAME = "bsinfo-projekt/database.properties";

    @BeforeAll
    public static void prepareConfig(@Shared(factory = TemporaryDirectory.class, name = "test_config.properties") Path configPath) throws IOException {
        @Language("properties")
        var config = """
                user.db.url=jdbc:postgresql://localhost:5432/bs
                user.db.user=bs
                user.db.password=bs
                """;

        Files.createDirectories(configPath.resolve(CONFIG_NAME).getParent());
        Files.writeString(configPath.resolve(CONFIG_NAME), config, StandardOpenOption.CREATE);
    }

    @Test
    public void testConfigRead(@Shared(factory = TemporaryDirectory.class, name = "test_config.properties") Path configPath) throws IOException {
        var config = Config.fromFile(configPath.resolve(CONFIG_NAME), "user");
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/bs", config.url());
        Assertions.assertEquals("bs", config.username());
        Assertions.assertEquals("bs", config.password());
    }

    @Test
    public void testConfigDefault(@Shared(factory = TemporaryDirectory.class, name = "test_config.properties") Path configPath) throws IOException {
        System.setProperty("user.name", "user");
        System.setProperty("user.home", configPath.toString());
        var config = Config.fromDefault();
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/bs", config.url());
        Assertions.assertEquals("bs", config.username());
        Assertions.assertEquals("bs", config.password());
    }
}
