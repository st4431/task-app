package com.takahata.task_app.repository;

import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setTaskStatusEnum(TaskStatus.valueOf(rs.getString("task_status")));
        if (rs.getDate("due_date") != null) {
            task.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        task.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return task;
    };

    //コンストラクタの引数が一つの時は、@Autowiredは省略可能　
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Task> findAllTasks() {
        return jdbcTemplate.query("""
                SELECT * FROM task;""", taskRowMapper);
    }
    public void registerNewTask(Task task) {
        jdbcTemplate.update("""
                INSERT INTO task(title, description, task_status, due_date)
                VALUES(?, ?, ?, ?)""",
                task.getTitle(), task.getDescription(), task.getTaskStatusEnum().name(), task.getDueDate());
    }
}
