package org.example.mapper;

import org.example.model.Project;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ProjectMapper implements EntityMapper<Project> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void mapToStatement(PreparedStatement statement, Project project) throws SQLException {
        statement.setString(1, project.getName());
        statement.setInt(2, project.getClient_id());
        statement.setObject(3, project.getStart_date().format(DATE_FORMATTER));
        statement.setObject(4, project.getFinish_date().format(DATE_FORMATTER));
//        statement.setDate(3, Date.valueOf(project.getStart_date()));
//        statement.setDate(4, Date.valueOf(project.getFinish_date()));
    }
}
