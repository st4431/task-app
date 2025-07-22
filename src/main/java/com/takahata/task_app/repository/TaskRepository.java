package com.takahata.task_app.repository;

import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setTaskStatus(TaskStatus.valueOf(rs.getString("task_status")));
        if (rs.getDate("due_date") != null) {
            task.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        task.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return task;
    };

    public List<Task> findAllTasks() {
        return jdbcTemplate.query("""
                SELECT * FROM task;""",
                taskRowMapper);
    }

    public void registerNewTask(Task task) {
        jdbcTemplate.update("""
                INSERT INTO task (
                title,
                description,
                task_status,
                due_date)
                VALUES(?, ?, ?, ?);""",
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus().name(),
                task.getDueDate());
    }

    public void deleteTask(int id) {
        jdbcTemplate.update("""
                DELETE FROM task
                WHERE id = ?;""",
                id);
    }

    public Task findTaskById(int id) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM task
                WHERE id = ?;""",
                taskRowMapper,
                id);
    }

    public void updateTask(Task updatedTask) {
        jdbcTemplate.update("""
                UPDATE task
                SET
                title = ?,
                description = ?,
                task_status = ?,
                due_date = ?
                WHERE id = ?;""",
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getTaskStatus().name(),
                updatedTask.getDueDate(),
                updatedTask.getId());
    }
}
