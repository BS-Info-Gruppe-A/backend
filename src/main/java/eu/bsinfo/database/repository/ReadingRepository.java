package eu.bsinfo.database.repository;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

public class ReadingRepository extends Repository<IReading> {

    /**
     * Constructor.
     *
     * @param databaseManager reference to the {@link DatabaseManager} to use
     *                        for database connections
     * @throws NullPointerException if databaseManager is null
     */
    public ReadingRepository(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        super(databaseManager);
    }

    @Override
    public @Nullable IReading findById(@NotNull UUID id) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try(var statement = connection.prepareStatement("""
                    SELECT * FROM readings WHERE id = ?
                    JOIN customers c on c.id = customer_id
                    """)) {
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
            try (var statement = connection.prepareStatement("INSERT INTO readings (id, comment, customer_id, read_date, meter_type, meter_count, meter_id, substitute) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                statement.setObject(1, entity.getId());
                statement.setString(2, entity.getComment());
                statement.setObject(3, entity.getCustomer().getId());
                statement.setTimestamp(4, Timestamp.valueOf(entity.getDateOfReading().atTime(0, 0)));
                statement.setObject(5, entity.getKindOfMeter(), Types.OTHER);
                statement.setDouble(6, entity.getMeterCount());
                statement.setString(7, entity.getMeterId());
                statement.setBoolean(8, entity.getSubstitute());
                return statement.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean update(@NotNull IReading entity) throws SQLException {
        try (var connection = databaseManager.getConnection()) {
            try (var statement = connection.prepareStatement("UPDATE readings SET comment = ?, customer_id = ?, read_date = ?, meter_type = ?,  meter_count = ?,  meter_id = ?, substitute = ? WHERE id = ?")) {
                statement.setString(1, entity.getComment());
                statement.setObject(2, entity.getCustomer().getId());
                statement.setTimestamp(3, Timestamp.valueOf(entity.getDateOfReading().atTime(0, 0)));
                statement.setObject(4, entity.getKindOfMeter(), Types.OTHER);
                statement.setDouble(5, entity.getMeterCount());
                statement.setString(6, entity.getMeterId());
                statement.setBoolean(7, entity.getSubstitute());
                statement.setObject(8,entity.getId());
                return statement.executeUpdate() > 0;
            }
        }
    }

}
