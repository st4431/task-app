package com.takahata.task_app.dto;

import com.takahata.task_app.entity.Task_StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TaskInputDto {
    @NotEmpty
    private String title;
    private String description;
    private Task_StatusEnum taskStatusEnum;
    private LocalDate dueDate;
}

