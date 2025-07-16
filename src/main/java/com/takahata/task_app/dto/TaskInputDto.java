package com.takahata.task_app.dto;

import com.takahata.task_app.entity.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskInputDto {
    private int id;
    @NotEmpty
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private LocalDate dueDate;
}

