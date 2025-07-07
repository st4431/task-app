package com.takahata.task_app.dto;

import com.takahata.task_app.entity.Task_StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

public class TaskInputDto {
    @NotNull
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private Task_StatusEnum taskStatusEnum;
    @Getter
    private Date dueDate;
}

