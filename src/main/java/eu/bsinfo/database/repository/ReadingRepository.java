package eu.bsinfo.database.repository;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.*;

/// Implementation of [Repository] for the `readings` table.
public class ReadingRepository extends Repository<IReading> {

    private static final Logger log = LoggerFactory.getLogger(ReadingRepository.class);

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

    /// {@inheritDoc}
    @Nullable
    @Override
    public IReading findById(@NotNull UUID id) throws SQLException {
        Objects.requireNonNull(id, "id cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     SELECT * FROM readings
                     JOIN customers c on c.id = customer_id
                     WHERE readings.id = ?
                     """)) {
            statement.setObject(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return IReading.from(resultSet);
                } else {
                    return null;
                }
            }
        }
    }

    /// {@inheritDoc}
    @NotNull
    @Override
    public List<IReading> getAll() throws SQLException {
        var output = new ArrayList<IReading>();
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     SELECT *
                     FROM readings
                     JOIN customers c ON c.id = readings.customer_id;
                     """)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    output.add(IReading.from(resultSet));
                }
            }
        }
        return output;
    }

    /// Finds an entity by its date and kindOfMeter.
    ///
    /// @param startDate   the date to search for (inclusive)
    /// @param endDate     the date to search for (inclusive)
    /// @param kindOfMeter the kindOfMeter to search to
    /// @param customerId  the id of the customer owning the reading
    /// @return the entity corresponding to the date and KindOfMeter or all Readings if none is found
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if date and kindOfMeter is null
    public List<IReading> getReadings(@Nullable LocalDate startDate, @Nullable LocalDate endDate, @Nullable IReading.KindOfMeter kindOfMeter, @Nullable UUID customerId) throws SQLException {
        var sqlStatement = new StringBuilder("SELECT * FROM readings JOIN customers c ON c.id = readings.customer_id WHERE readings.read_date BETWEEN ? AND ?");
        var output = new ArrayList<IReading>();
        var safeStartDate = Optional.ofNullable(startDate).orElse(LocalDate.MIN);
        var safeEndDate = Optional.ofNullable(endDate).orElse(LocalDate.MAX);

        try (var connection = databaseManager.getConnection()) {
            if (customerId != null) {
                sqlStatement.append(' ');
                sqlStatement.append("AND readings.customer_id = ?");
            }
            if (kindOfMeter != null) {
                sqlStatement.append(' '); // Ad space between previous statement and new statement
                sqlStatement.append("AND readings.meter_type = ?;");
            } else {
                sqlStatement.append(';');
            }
            var statement = connection.prepareStatement(sqlStatement.toString());
            var parameterIndex = 0;
            statement.setObject(++parameterIndex, safeStartDate);
            statement.setObject(++parameterIndex, safeEndDate);
            if (customerId != null) {
                statement.setObject(++parameterIndex, customerId);
            }
            if (kindOfMeter != null) {
                statement.setObject(++parameterIndex, kindOfMeter, Types.OTHER);
            }
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    output.add(IReading.from(resultSet));
                }
                return output;
            }
        }
    }

    /// {@inheritDoc}
    @Override
    public boolean delete(@NotNull UUID id) throws SQLException {
        Objects.requireNonNull(id, "id cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("DELETE FROM readings WHERE id = ?")) {
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;

        }
    }

    /// {@inheritDoc}
    @Override
    public boolean insert(@NotNull IReading entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     INSERT INTO readings(id, comment, customer_id, read_date, meter_type, meter_count, meter_id, substitute)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?)""")) {
            statement.setObject(1, entity.getId());
            statement.setString(2, entity.getComment());
            statement.setObject(3, entity.getCustomer().getId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getDateOfReading().atTime(0, 0)));
            statement.setObject(5, entity.getKindOfMeter(), Types.OTHER);
            statement.setDouble(6, entity.getMeterCount());
            statement.setInt(7, entity.getMeterId());
            statement.setBoolean(8, entity.getSubstitute());
            return statement.executeUpdate() > 0;
        }
    }

    /// {@inheritDoc}
    @Override
    public boolean update(@NotNull IReading entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     UPDATE readings SET comment = ?, customer_id = ?, read_date = ?, meter_type = ?,  meter_count = ?,
                     meter_id = ?, substitute = ? WHERE id = ?""")) {
            statement.setString(1, entity.getComment());
            statement.setObject(2, entity.getCustomer().getId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateOfReading().atTime(0, 0)));
            statement.setObject(4, entity.getKindOfMeter(), Types.OTHER);
            statement.setDouble(5, entity.getMeterCount());
            statement.setInt(6, entity.getMeterId());
            statement.setBoolean(7, entity.getSubstitute());
            statement.setObject(8, entity.getId());
            return statement.executeUpdate() > 0;
        }
    }
}
