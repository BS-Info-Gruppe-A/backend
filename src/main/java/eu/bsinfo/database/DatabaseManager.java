package eu.bsinfo.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/// Manager of Database connections.
public class DatabaseManager {
    private final DataSource dataSource;

    /// Constructor using default pool size (10).
    ///
    /// @param url      the database url to use
    /// @param user     the user to use
    /// @param password the password to use
    /// @throws NullPointerException if a parameter is null
    public DatabaseManager(@NotNull String url, @Nullable String user, @Nullable String password) {
        this(url, user, password, 10);
    }

    /// Constructor.
    ///
    /// @param url             the database url to use
    /// @param user            the user to use
    /// @param password        the password to use
    /// @param maximumPoolSize the maximum connection pool size
    /// @throws NullPointerException if a parameter is null
    public DatabaseManager(@NotNull String url, @Nullable String user, @Nullable String password, int maximumPoolSize) {
        Objects.requireNonNull(url, "url cannot be null");

        var config = new HikariConfig();

        config.setJdbcUrl(url);
        if (user != null) {
            config.setUsername(user);
        }
        if (password != null) {
            config.setPassword(password);
        }
        config.setMaximumPoolSize(maximumPoolSize);

        dataSource = new HikariDataSource(config);
    }

    /// Creates a new single-use connection.
    /// This connection should be closed after usage using a `try-with-resources` block
    ///
    /// @return the newly created [Connection]
    /// @throws SQLException if a database access error occurs
    @NotNull
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
