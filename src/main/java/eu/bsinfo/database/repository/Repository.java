package eu.bsinfo.database.repository;

import eu.bsinfo.database.DatabaseManager;
import eu.bsinfo.entity.ICustomer;
import eu.bsinfo.entity.IId;
import eu.bsinfo.entity.IReading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/// Abstract representation of a repository.
/// @param <T> the type of entity stored in this repository
public abstract class Repository<T extends IId> {
    protected final DatabaseManager databaseManager;

    /// Constructor.
    /// @param databaseManager reference to the [DatabaseManager] to use
    ///                        for database connections
    /// @throws NullPointerException if databaseManager is null
    protected Repository(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        this.databaseManager = databaseManager;
    }

    /// Finds an entity by its id.
    /// @param id the id to search for
    /// @throws SQLException if an SQL error occurs
    /// @throws NullPointerException if id is null
    /// @return the entity corresponding to the id or `null` if none is found
    @Nullable
    public abstract T findById(@NotNull UUID id) throws SQLException;

    /// Gives all entities.
     /// @throws SQLException if an SQL error occurs
     /// @return all entities inside a table
    public abstract List<T> getAll() throws SQLException;

    /// Finds an entity by its date and kindOfMeter.
    /// @param date: the date to search for
     /// @param kindOfMeter: the kindOfMeter to search to
     /// @throws SQLException if an SQL error occurs
     /// @throws NullPointerException if date and kindOfMeter is null
     /// @return the entity corresponding to the date and KindOfMeter or `null` if none is found
    public abstract List<IReading> getReadings(LocalDate date, IReading.KindOfMeter kindOfMeter) throws SQLException;


        /// Delete an entity by its id.
        ///
        /// @param id the id of the entity to delete
        /// @throws SQLException         if an SQL error occurs
        /// @throws NullPointerException if entity is null
        /// @return whether an entity was deleted or not
    public abstract boolean delete(@NotNull UUID id) throws SQLException;

    /// Deletes an entity from the database.
    ///
    /// @param entity the entity to delete
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    /// @return whether an entity was deleted or not
    public boolean delete(@NotNull T entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        return delete(entity.getId());
    }

    /// Inserts a new entity into the database.
    ///
    /// @param entity the entity to insert
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    /// @return whether an entity was inserted or not
    public abstract boolean insert(@NotNull T entity) throws SQLException;

    /// Updates an entity in the database.
    ///
    /// @param entity the entity to update
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    /// @return whether the entity was updated or not
    public abstract boolean update(@NotNull T entity) throws SQLException;

    /// Convenience method to insert or update an entity in the database.
    /// this will create a new entity if it has not been saved yet or update it
    /// otherwise.
    /// @param entity the entity to save
    /// @throws SQLException if an SQL error occurs
    /// @throws NullPointerException if entity is null
    /// @return whether an update occurred
    public boolean save(@NotNull T entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        if (findById(entity.getId()) == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
}
