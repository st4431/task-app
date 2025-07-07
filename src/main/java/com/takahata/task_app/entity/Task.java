package com.takahata.task_app.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
