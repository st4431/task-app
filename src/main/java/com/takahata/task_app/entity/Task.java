package com.takahata.task_app.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Task {
    private int id;
    private String title;
    private String description;
    private Task_StatusEnum taskStatusEnum;
    private Date dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
