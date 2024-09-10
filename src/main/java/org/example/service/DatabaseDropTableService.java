package org.example.service;

import com.codahale.metrics.MetricRegistry;
import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseDropTableService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDropTableService.class);
    private final ConnectionManager connectionManager;
    private final MetricRegistry metricRegistry;

    public DatabaseDropTableService(ConnectionManager connectionManager, MetricRegistry metricRegistry) {
        this.connectionManager = connectionManager;
        this.metricRegistry = metricRegistry;
    }

    public void dropAllTables(String sqlFilePath) {
        Path path = Paths.get(sqlFilePath);
        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection(), metricRegistry)) {
            String sqlContent = new String(Files.readAllBytes(path));
            executor.executeBatch(sqlContent);
        } catch (IOException e) {
            logger.error("Failed to read SQL file: {}", e.getMessage());
        }
    }

    public void dropAllTables() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT tablename FROM pg_tables WHERE schemaname = 'public'");

            while (resultSet.next()) {
                String tableName = resultSet.getString("tablename");
                dropTable(connection, tableName);
            }
        } catch (Exception e) {
            logger.error("Failed to drop all tables {}", e.getMessage());
        }
    }

    private void dropTable(Connection connection, String tableName) {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName + " CASCADE";
        try {
            SQLExecutor executor = new SQLExecutor(connection, metricRegistry);
            executor.executeUpdate(dropTableSQL);
            logger.info("Table '{}' dropped successfully.", tableName);
        } catch (Exception e) {
            logger.error("Failed to drop table '{}': {}", tableName, e.getMessage());
        }
    }
}
