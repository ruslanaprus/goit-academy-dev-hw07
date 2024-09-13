# SQL Database Operations Manager (Prepared Statement Upgrade)

## Overview

This project is an enhanced version of the [SQL Database Operations Manager](https://github.com/ruslanaprus/goit-academy-dev-hw06), upgraded to use **`Prepared Statements`** instead of the traditional `Statement`. The core objective of this project remains to facilitate database management operations, including schema initialisation, data population, and querying, while also providing support for multiple database systems like PostgreSQL and SQLite.

This is solution for GoIT Academy Module 2.07 [Extended work with JDBC] hometask.

## Key Changes in This Version

### 1. Migration to Prepared Statements

- The project now uses `PreparedStatement` to execute SQL queries instead of `Statement`. This improves **security** by mitigating **SQL injection attacks** and enhances **performance** when executing the same query multiple times.
- SQL statements and queries remain stored in external files under the `src/main/resources/sql/{database_type}/` folder, but **placeholders** (`?`) are used in the SQL scripts for parameters. These placeholders are populated with actual values using **PreparedStatement**.
### 2. Seed Data Injection Using Prepared Statements

- The project reads predefined lists of entities from the `Seed` class, including `Worker`, `Client`, `Project`, and `ProjectWorker`, and populates the database by mapping these entities to parameterised SQL statements.
- This implementation provides a more structured and secure approach to inserting data into the database, ensuring parameters are correctly handled and mapped to their respective SQL queries.

## Advantages of Prepared Statements in This Project

- **SQL Injection Prevention**: By using `PreparedStatement`, we prevent the risk of SQL injection, a common vulnerability when user inputs are directly incorporated into SQL statements.
- **Improved Performance**: `PreparedStatement` allows the SQL engine to precompile the SQL statement once and reuse it multiple times, which is more efficient for batch operations.
- **Code Clarity**: Prepared statements make it easier to read and maintain code by clearly separating the SQL template from the parameters being passed.
- **Flexibility**: The project retains flexibility as SQL files are still loaded from external files. The parameter placeholders are replaced with actual values from the seed data programmatically, ensuring SQL templates remain clean and database-agnostic.

## Key Components of the Upgrade

### 1. **GenericDatabaseService<`T`>**

- Introduced to handle common database operations like inserting entities into the database using `PreparedStatement`.
- The `insertEntities` method takes an SQL string with placeholders and a list of entities, and uses an `EntityMapper` to map the entities to the statement's parameters.
- **Advantages**: Centralises and standardises the way batch inserts are performed across various entity types (e.g., Worker, Client, Project).

```java
public void insertEntities(String sql, List<T> entities, EntityMapper<T> mapper) {
    try (Connection connection = connectionManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        for (T entity : entities) {
            mapper.mapToStatement(statement, entity);
            statement.addBatch();
        }
        statement.executeBatch();
    }
}
```

### 2. **DatabasePopulateService**

- Updated to populate the database using lists of pre-defined entities (seed data) and prepared statements.
- Instead of manually constructing SQL strings with values, data insertion is handled via **parameterised SQL** with placeholders for each entity field.

```java
workerService.insertEntities(
    "INSERT INTO worker (name, birthday, email, level, salary) VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING", 
    workers, 
    new WorkerMapper()
);
```

### 3. **Mappers (EntityMapper<`T`> and Implementations)**

- The **EntityMapper** interface and its implementations (e.g., `WorkerMapper`, `ClientMapper`, `ProjectMapper`) map each field of an entity to the corresponding placeholder in the SQL query.
- This design allows for flexible changes to how each entity is inserted into the database while ensuring code reusability and clarity.
```java
@Override
public void mapToStatement(PreparedStatement statement, Worker worker) throws SQLException {
    statement.setString(1, worker.getName());
    statement.setDate(2, Date.valueOf(worker.getDateOfBirth()));
    statement.setString(3, worker.getEmail());
    statement.setString(4, worker.getLevel().name().toLowerCase());
    statement.setInt(5, worker.getSalary());
}
```

### 4. **SQLExecutor**

- Refactored to use `PreparedStatement` for executing both simple and batch SQL updates.
- The `executeUpdate`,  `executeBatch` and `executeQuery` methods are now implemented with `PreparedStatement` .

### 5. **Seed Class**

- Provides seed data (`workers`, `clients`, `projects`, `projectWorkers`) used to populate the database. This data is injected into the database via prepared statements in the `DatabasePopulateService`.
- This approach simplifies database population during development and testing.

## SQL File Structure

The SQL scripts remain stored in external files, but their content has been updated to use **parameterised queries**:

```sql
-- populate_db.sql
INSERT INTO worker (name, birthday, email, level, salary)
VALUES (?, ?, ?, ?, ?)
ON CONFLICT DO NOTHING;
```

## Project Structure Overview (Post-Upgrade)

- **`AppLauncher`**: The main entry point for initializsing and populating the database.
- **`GenericDatabaseService<T>`**: Manages entity-based database operations (e.g., inserting entities using `PreparedStatement`).
- **`DatabasePopulateService`**: Uses seed data to populate the database with workers, clients, projects, and project-worker relationships.
- **`EntityMapper<T>` Interface and Mappers**: Define how each entity type is mapped to SQL query parameters.
- **`SQLExecutor`**: Manages SQL execution (including querying and updates) using `PreparedStatement`.
- **`Seed`**: Provides predefined lists of entities (workers, clients, etc.) to be inserted into the database.
  Other classes from original project remain unchanged.

## Folder Structure

- `org.example`: Main classes and entry point.
- `org.example.db`: Database connection management and SQL execution.
- `org.example.service`: Services for database initialization and population.
- `org.example.mapper`: Mappers for transforming entities into SQL parameters.
- `org.example.model`: Data models for `Worker`, `Client`, `Project`, and `ProjectWorker`.
- `org.example.seed`: Contains seed data used to populate the database.

## Switching Between Databases

The app can easily switch between different database types (e.g., PostgreSQL, SQLite) by changing the `DatabaseType` enum value in `AppLauncher`. Switching between databases is as simple as creating an instance of a different `Database` implementation. The `DatabaseType` enum, `Database` interface and the `ConnectionManager`'s dependency injection model allow you to easily switch between these databases without altering the core logic.

To switch between databases:

1. Make sure you have correct database credentials in your environment variables as defined in `config.properties`.
2. Update the `DatabaseType` in `AppLauncher` to either `POSTGRES` or `SQLITE`.

For example, to use SQLite, update the `DatabaseType` to `SQLITE`:

```java
//SQLite database setup  
runDatabaseOperations(DatabaseType.SQLITE, metricRegistry);  
```

To use PostgresQL, update the `DatabaseType` to `POSTGRES`:

```java
// Postgres database setup       
runDatabaseOperations(DatabaseType.POSTGRES, metricRegistry);
```

The SQL queries specific to each database are stored in separate files, allowing the application to load the appropriate queries dynamically at runtime. This design allows for easy integration of new databases with minimal changes to the existing codebase.

## Usage Instructions

1. **[Choose your database](#5-switching-between-databases)**: PostgresQL or SQLite.

2. **Configure Database Credentials**: Ensure that the environment variables or the properties file is correctly set up.

Edit `config.properties` with your database URL, username, and password for the database you plan to use. For example, for PostgresQL you can set environment variables `POSTGRES_DB_URL`, `POSTGRES_DB_USER`, `POSTGRES_DB_PASS` and use them in properties file like this:
```properties
postgres.db.url=${POSTGRES_DB_URL}
postgres.db.user=${POSTGRES_DB_USER}
postgres.db.password=${POSTGRES_DB_PASS}
```

3. **Build the Application**: Use gradle or your preferred build tool to build the application:

```shell
./gradlew clean build
```

4. **Run the Application**: Once the configuration is set up, you can run the application by executing the main class `AppLauncher`. Ensure that the SQL scripts are properly set in the `resources` directory.

```shell
java -jar build/libs/dbcat.jar
```

5. **Query Operations**: SQL queries are read from external files. To change SQL logic, simply modify the `.sql` files located in your resources folder. The application automatically runs queries as defined in the SQL files located in the respective database directory. 