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
///
/// @param <T> the type of entity stored in this repository
public abstract class Repository<T extends IId> {
    protected final DatabaseManager databaseManager;

    /// Constructor.
    ///
    /// @param databaseManager reference to the [DatabaseManager] to use
    ///                                               for database connections
    /// @throws NullPointerException if databaseManager is null
    protected Repository(@NotNull DatabaseManager databaseManager) {
        Objects.requireNonNull(databaseManager, "databaseManager cannot be null");
        this.databaseManager = databaseManager;
    }

    /// Finds an entity by its id.
    ///
    /// @param id the id to search for
    /// @return the entity corresponding to the id or `null` if none is found
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if id is null
    @Nullable
    public abstract T findById(@NotNull UUID id) throws SQLException;

    /// Gives all entities.
    ///
    /// @return all entities inside a table
    /// @throws SQLException if an SQL error occurs
    public abstract List<T> getAll() throws SQLException;


    /// Delete an entity by its id.
    ///
    /// @param id the id of the entity to delete
    /// @return whether an entity was deleted or not
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    public abstract boolean delete(@NotNull UUID id) throws SQLException;

    /// Deletes an entity from the database.
    ///
    /// @param entity the entity to delete
    /// @return whether an entity was deleted or not
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    public boolean delete(@NotNull T entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        return delete(entity.getId());
    }

    /// Inserts a new entity into the database.
    ///
    /// @param entity the entity to insert
    /// @return whether an entity was inserted or not
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    public abstract boolean insert(@NotNull T entity) throws SQLException;

    /// Updates an entity in the database.
    ///
    /// @param entity the entity to update
    /// @return whether the entity was updated or not
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    public abstract boolean update(@NotNull T entity) throws SQLException;

    /// Convenience method to insert or update an entity in the database.
    /// this will create a new entity if it has not been saved yet or update it
    /// otherwise.
    ///
    /// @param entity the entity to save
    /// @return whether an update occurred
    /// @throws SQLException         if an SQL error occurs
    /// @throws NullPointerException if entity is null
    public boolean save(@NotNull T entity) throws SQLException {
        Objects.requireNonNull(entity, "entity cannot be null");
        if (findById(entity.getId()) == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
}
