package com.takahata.task_app.dto;

import com.takahata.task_app.entity.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskInputDto {
    @NotEmpty
    private String title;
    private String description;
    private TaskStatus taskStatusEnum;
    private LocalDate dueDate;
}

