package eu.bsinfo.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manager of Database connections.
 */
public class DatabaseManager {
    private final DataSource dataSource;

    /**
     * Constructor.
     * @param url the database url to use
     * @param user the user to use
     * @param password the password to use
     */
    public DatabaseManager(String url, String user, String password) {
        var config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");

        dataSource = new HikariDataSource(config);
    }

    /**
     * Creates a new single-use connection.
     * This connection should be closed after usage using a {@code try-with-resources} block
     *
     * @return the newly created {@link Connection}
     * @throws SQLException if a database access error occurrs
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
