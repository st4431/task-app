package com.takahata.task_app.repository;

import com.takahata.task_app.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    //コンストラクタの引数が一つの時は、@Autowiredは省略可能　
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerNewTask(Task task) {
        jdbcTemplate.update("""
                INSERT INTO task(title, description, task_status, due_date) 
                VALUES(?, ?, ?, ?)""",
                task.getTitle(), task.getDescription(), task.getTaskStatusEnum(), task.getDueDate());
    }
}
