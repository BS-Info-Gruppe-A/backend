package eu.bsinfo.database.repository;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.ICustomer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// [Repository] implementation for `customers` table.
public class CustomerRepository extends Repository<ICustomer> {
    /// Constructor.
    ///
    /// @param databaseManager reference to the [DatabaseManager] to use
    ///                                                                                             for database connections
    /// @throws NullPointerException if databaseManager is null
    public CustomerRepository(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        super(databaseManager);
    }

    /// {@inheritDoc}
    @Override
    @Nullable
    public ICustomer findById(@NotNull UUID id) throws SQLException {
        Objects.requireNonNull(id, "id cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
            statement.setObject(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return ICustomer.from(resultSet);
                } else {
                    return null;
                }
            }

        }
    }

    /// {@inheritDoc}
    @NotNull
    @Override
    public List<ICustomer> getAll() throws SQLException {
        var output = new ArrayList<ICustomer>();
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("SELECT * FROM customers")) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    output.add(ICustomer.from(resultSet));
                }
            }
        }
        return output;
    }

    /// {@inheritDoc}
    @Override
    public boolean delete(@NotNull UUID id) throws SQLException {
        Objects.requireNonNull(id, "id cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("DELETE FROM customers WHERE id = ?")) {
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    /// {@inheritDoc}
    @Override
    public boolean insert(@NotNull ICustomer entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     INSERT INTO customers(id, birth_date, first_name, gender, last_name) VALUES (?, ?, ?, ?, ?)""")) {
            statement.setObject(1, entity.getId());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getBirthDate().atTime(0, 0)));
            statement.setString(3, entity.getFirstName());
            statement.setObject(4, entity.getGender(), Types.OTHER);
            statement.setString(5, entity.getLastName());
            return statement.executeUpdate() > 0;

        }
    }

    /// {@inheritDoc}
    @Override
    public boolean update(@NotNull ICustomer entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement("""
                     UPDATE customers SET birth_date = ?,\
                      first_name = ?, gender = ?, last_name = ? WHERE id = ?""")) {
            statement.setTimestamp(1, Timestamp.valueOf(entity.getBirthDate().atTime(0, 0)));
            statement.setString(2, entity.getFirstName());
            statement.setObject(3, entity.getGender(), Types.OTHER);
            statement.setString(4, entity.getLastName());
            statement.setObject(5, entity.getId());
            return statement.executeUpdate() > 0;
        }
    }
}
