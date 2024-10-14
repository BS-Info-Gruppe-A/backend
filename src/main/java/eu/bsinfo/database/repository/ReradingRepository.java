package eu.bsinfo.database.repository;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

public class ReradingRepository extends Repository<IReading>{

    /**
     * Constructor.
     *
     * @param databaseManager reference to the {@link DatabaseManager} to use
     *                        for database connections
     * @throws NullPointerException if databaseManager is null
     */
    public ReradingRepository(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        super(databaseManager);
    }

    @Override
    public @Nullable IReading findById(@NotNull UUID id) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try(var statement = connection.prepareStatement("SELECT * FROM readings WHERE id = ?")) {
                statement.setObject(1, id);
                try(var resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return IReading.from(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    @Override
    public boolean delete(@NotNull UUID id) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try (var statement = connection.prepareStatement("DELETE FROM readings WHERE id = ?")) {
                statement.setObject(1, id);
                return statement.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean insert(@NotNull IReading entity) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try (var statement = connection.prepareStatement("INSERT INTO readings (id, birth_date, first_name, gender, last_name) VALUES (?, ?, ?, ?, ?)")) {
                statement.setObject(1, entity.getId());
                statement.setTimestamp(2, Timestamp.valueOf(entity.getBirthDate().atTime(0, 0)));
                statement.setString(3, entity.getFirstName());
                statement.setObject(4, entity.getGender(), Types.OTHER);
                statement.setString(5, entity.getLastName());
                return statement.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean update(@NotNull IReading entity) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try (var statement = connection.prepareStatement("UPDATE readings SET birth_date = ?, first_name = ?, gender = ?, last_name = ? WHERE id = ?")) {
                statement.setTimestamp(1, Timestamp.valueOf(entity.getBirthDate().atTime(0, 0)));
                statement.setString(2, entity.getFirstName());
                statement.setObject(3, entity.getGender(), Types.OTHER);
                statement.setString(4, entity.getLastName());
                statement.setObject(5, entity.getId());
                return statement.executeUpdate() > 0;
            }
        }
    }

}
