package org.example.service;

import com.codahale.metrics.MetricRegistry;
import org.example.db.ConnectionManager;
import org.example.mapper.ClientMapper;
import org.example.mapper.ProjectMapper;
import org.example.mapper.WorkerMapper;
import org.example.model.Client;
import org.example.model.Project;
import org.example.model.Worker;
import org.example.seed.Seed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DatabasePopulateService {
    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulateService.class);
    private final ConnectionManager connectionManager;
    private final MetricRegistry metricRegistry;
    private final GenericDatabaseService<Worker> workerService;
    private final GenericDatabaseService<Client> clientService;
    private final GenericDatabaseService<Project> projectService;

    public DatabasePopulateService(ConnectionManager connectionManager, MetricRegistry metricRegistry) {
        this.connectionManager = connectionManager;
        this.metricRegistry = metricRegistry;
        this.workerService = new GenericDatabaseService<>(connectionManager, metricRegistry);
        this.clientService = new GenericDatabaseService<>(connectionManager, metricRegistry);
        this.projectService = new GenericDatabaseService<>(connectionManager, metricRegistry);
    }

    public void seedDatabase() {
        List<Worker> workers = Seed.workers;
        List<Client> clients = Seed.clients;
        List<Project> projects = Seed.projects;

        logger.info("Number of workers: {}", workers.size());
        logger.info("Number of clients: {}", clients.size());
        logger.info("Number of projects: {}", projects.size());

        workerService.insertEntities("INSERT INTO worker (name, birthday, email, level, salary) VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING", workers, new WorkerMapper());
        clientService.insertEntities("INSERT INTO client (name) VALUES (?) ON CONFLICT DO NOTHING", clients, new ClientMapper());
        projectService.insertEntities("INSERT INTO project (name, client_id, start_date, finish_date) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING", projects, new ProjectMapper());
        logger.info("Database seeding completed successfully!");
    }

    /**
     * Adds a list of workers, clients, and projects to the database based on the provided SQL file.
     */
    public void addEntities(String sqlFilePath, List<Worker> workers, List<Client> clients, List<Project> projects) {
        try {
            Path path = Paths.get(sqlFilePath);
            String sqlContent = new String(Files.readAllBytes(path));

            String[] sqlStatements = sqlContent.split(";");

            for (String sqlStatement : sqlStatements) {
                sqlStatement = sqlStatement.trim();
                if (sqlStatement.startsWith("INSERT INTO worker")) {
                    workerService.insertEntities(sqlStatement, workers, new WorkerMapper());
                } else {
                    logger.warn("Unknown SQL statement: {}", sqlStatement);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read SQL file: {}", e.getMessage());
        }
    }
}

//package org.example.service;
//
//import com.codahale.metrics.MetricRegistry;
//import org.example.db.ConnectionManager;
//import org.example.db.SQLExecutor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class DatabasePopulateService {
//    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulateService.class);
//    private final ConnectionManager connectionManager;
//    private final MetricRegistry metricRegistry;
//
//    public DatabasePopulateService(ConnectionManager connectionManager, MetricRegistry metricRegistry) {
//        this.connectionManager = connectionManager;
//        this.metricRegistry = metricRegistry;
//    }
//
//    /**
//     * Inserts data into the database by executing SQL scripts.
//     */
//    public void insertData(String sqlFilePath) {
//        Path path = Paths.get(sqlFilePath);
//        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection(), metricRegistry)) {
//            String sqlContent = new String(Files.readAllBytes(path));
//            executor.executeBatch(sqlContent);
//        } catch (IOException e) {
//            logger.error("Failed to read SQL file: {}", e.getMessage());
//        }
//    }
//}