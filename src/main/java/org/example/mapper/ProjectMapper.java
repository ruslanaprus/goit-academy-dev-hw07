package org.example.mapper;

import org.example.model.Project;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectMapper implements EntityMapper<Project> {

    @Override
    public void mapToStatement(PreparedStatement statement, Project project) throws SQLException {
        statement.setString(1, project.getName());
        statement.setInt(2, project.getClient_id());
        statement.setDate(3, Date.valueOf(project.getStart_date()));
        statement.setDate(4, Date.valueOf(project.getFinish_date()));
    }
}
