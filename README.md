# SQL Database operations manager

## Overview

This project is designed to facilitate the management of SQL database connections, initialisation, data population, and querying using a flexible, configurable architecture. The project supports different database systems (PostgreSQL, SQLite) and provides a seamless way to switch between them. SQL queries and statements are loaded from files, which improves flexibility and maintainability by allowing database operations to be modified without changing the codebase. This is solution for GoIT Academy Module 2.06 [JDBC Basics] hometask.


## Project Structure

The project is organised into multiple components that handle various responsibilities:

- **`AppLauncher`**: The main class that orchestrates database initialisation, data insertion, and query execution.
- **`Database` (Interface)**: Defines the contract for creating data sources for different database systems (e.g., PostgreSQL, SQLite).
- **`ConnectionManager`**: Manages database connections and ensures efficient connection pooling.
- **`ConfigLoader`**: Loads and resolves configuration properties for database credentials.
- **`SQLExecutor`**: Executes SQL statements and queries while tracking execution time through the `MetricsLogger`.
- **`MetricsLogger`**: Monitors database metrics such as connection times and query execution performance using Codahale Metrics.
- **`DatabaseServiceFactory`**: Provides services to initialise, populate, and query the database.
- **`Database Services (`DatabaseInitService`,` DatabasePopulateService`,` DatabaseQueryService`)`**: Perform specific tasks like database initialisation, data insertion, and executing queries.

### Folder Structure

- `org.example`: Contains the main classes and entry point.
- `org.example.db`: Handles database connections and query execution.
- `org.example.service`: Implements services for database initialisation, population, and querying.
- `org.example.config`: Loads and manages configuration properties.
- `org.example.constants`: Contains information about SQL files location and `DatabaseType` enum.
- `org.example.log`: Implements logging for performance metrics.

## Key Features

### 1. Flexible SQL Query Management

SQL statements are stored in external files located in `src/main/resources/sql/{database_type}/`. Each database type (PostgreSQL or SQLite) has its own folder containing the SQL scripts, which include:

- `init.sql`: Initialises the database schema.
- `populate.sql`: Populates the database with data.
- `{query}.sql`: various files that contain various query scripts.

This approach allows you to:

- **Modify SQL statements without changing code**. The paths to these files are generated based on the selected database type, ensuring that the correct SQL scripts are executed for the respective database.
- **Easily support multiple databases** by switching between database types, which automatically loads SQL from the directory corresponding to the specific database.

To add a new query to the `DatabaseQueryService` and display its results in the `AppLauncher`, follow these steps:
- Create a new `.sql` file and save it in the appropriate directory (`sql/postgres/` or `sql/sqlite/` depending on the database type).
- In the `DatabaseQueryService` class, add a new method to handle this query. The method should be similar to the existing ones, following the pattern of passing the SQL file path and mapping the result set to a Java object.
- In the `Constants` class, define a constant for the new SQL file path.
- In the `AppLauncher`, you need to compose the SQL file path and call the new method within `runDatabaseOperations`.
### 2. Connection Management

Connections to the database are managed by the `ConnectionManager` class, which uses a connection pooling mechanism via HikariCP to efficiently handle multiple concurrent database requests while keeping the application performance optimal. The `ConnectionManager` is a **singleton** to ensure that only one instance manages the pool, reducing overhead and improving efficiency.
#### Connection Lifecycle:

- The database connection is initialised using a `DataSource`, which is created by reading the configuration from the `config.properties` file via the `ConfigLoader`.
- The connection pool is monitored with the `MetricRegistry` to track connection usage and other performance metrics.
- The application retrieves connections from the pool when needed and releases them back after use.

**Connection Pooling** improves performance by reusing connections instead of creating a new one for every request. The pool detects connection leaks, preventing potential issues caused by unclosed connections. Configured timeouts ensure that requests do not hang indefinitely.

### 3. Singleton Pattern with Dependency Injection

The `ConnectionManager` follows the **singleton pattern** to ensure that there is only one instance managing the database connections. This singleton is combined with **dependency injection** via the `getInstance(Database database, MetricRegistry metricRegistry)` method, allowing you to inject different database types (e.g., PostgreSQL or SQLite) at runtime, making the system highly flexible and extensible.

### 4. Secure Credential Management

The `ConfigLoader` loads database credentials from both environment variables and properties files, ensuring that sensitive information (e.g., database URL, user, password) is never hardcoded in the source code. This approach provides **better security** by isolating credentials from the codebase and ensuring that credentials are retrieved securely.

#### Configuration Setup

The file `config.properties` contains the configuration for both PostgreSQL and SQLite databases. This file points to credentials stored securely elsewhere.

```properties
# Postgres Database Configurations
postgres.db.url=${POSTGRES_DB_URL}
postgres.db.user=${POSTGRES_DB_USER}
postgres.db.password=${POSTGRES_DB_PASS}

# SQLite Database Configurations
sqlite.db.url=jdbc:sqlite:src/main/resources/sql/sqlite/bob.db
sqlite.db.user=
sqlite.db.password=

```

- **Environment Variable Support**: The configuration uses placeholders that are replaced by environment variables, making it easy to configure for different environments.

### 5. Switching Between Databases

The app can easily switch between different database types (e.g., PostgreSQL, SQLite) by changing the `DatabaseType` enum value in `AppLauncher`. Switching between databases is as simple as creating an instance of a different `Database` implementation. The `DatabaseType` enum, `Database` interface and the `ConnectionManager`'s dependency injection model allow you to easily switch between these databases without altering the core logic.

To switch between databases:

1. Make sure you have correct database credentials in your environment variables as [defined](#configuration-setup) in `config.properties`.
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
### 6. Metrics Logging with `MetricsLogger`

The `MetricsLogger` tracks performance metrics for the database, such as:

- **Connection pool performance**.
- **Query execution times**.
- **Batch execution times**.

These metrics are logged at regular intervals and can be used to monitor database performance and optimise query efficiency. The metrics are tracked using Codahale's Metrics library, and the logs are outputted via SLF4J.

```java
MetricsLogger.startLogging(metricRegistry);
```
### 7. Performance and Connection Pooling

The project uses **HikariCP** to manage database connections, which provides:

- **Efficient connection pooling** to reduce the overhead of repeatedly opening/closing database connections.
- **Optimised performance** with configurable timeouts, max pool size, and connection caching.

HikariCP configurations are defined for both **PostgreSQL** and **SQLite**, with properties such as connection timeout, idle timeout, max pool size, etc., to fine-tune the performance.

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