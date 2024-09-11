package org.example.mapper;

import org.example.model.Worker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class WorkerMapper implements EntityMapper<Worker> {
    @Override
    public void mapToStatement(PreparedStatement statement, Worker worker) throws SQLException {
        statement.setString(1, worker.getName());
        statement.setDate(2, Date.valueOf(worker.getDateOfBirth()));
        statement.setString(3, worker.getEmail());
        statement.setString(4, worker.getLevel().name().toLowerCase());
        statement.setInt(5, worker.getSalary());
    }
}