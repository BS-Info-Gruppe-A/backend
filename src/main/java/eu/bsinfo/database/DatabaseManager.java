package eu.bsinfo.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/// Manager of Database connections.
public class DatabaseManager {
    private final DataSource dataSource;

    /// Constructor using default pool size (10).
    /// @param url the database url to use
    /// @param user the user to use
    /// @param password the password to use
    public DatabaseManager(String url, String user, String password) {
        this(url, user, password, 10);
    }

    /// Constructor.
    /// @param url the database url to use
    /// @param user the user to use
    /// @param password the password to use
    /// @param maximumPoolSize the maximum connection pool size
    public DatabaseManager(String url, String user, String password, int maximumPoolSize) {
        var config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(maximumPoolSize);

        dataSource = new HikariDataSource(config);
    }

    /// Creates a new single-use connection.
    /// This connection should be closed after usage using a `try-with-resources` block
    ///
    /// @return the newly created [Connection]
    /// @throws SQLException if a database access error occurs
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
