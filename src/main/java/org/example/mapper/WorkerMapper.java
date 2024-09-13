package org.example.mapper;

import org.example.model.Worker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class WorkerMapper implements EntityMapper<Worker> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void mapToStatement(PreparedStatement statement, Worker worker) throws SQLException {
        statement.setString(1, worker.getName());
        statement.setObject(2, worker.getDateOfBirth().format(DATE_FORMATTER));
//        statement.setDate(2, Date.valueOf(worker.getDateOfBirth()));
        statement.setString(3, worker.getEmail());
        statement.setString(4, worker.getLevel().name().toLowerCase());
        statement.setInt(5, worker.getSalary());
    }
}