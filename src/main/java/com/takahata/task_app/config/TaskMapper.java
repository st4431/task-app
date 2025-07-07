package com.takahata.task_app.config;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toTask(TaskInputDto taskInputDto) {
        Task task = new Task();
        task.setTitle(taskInputDto.getTitle());
        task.setDescription(taskInputDto.getDescription());
        task.setTaskStatus(taskInputDto.getTaskStatus());
        task.setDueDate(taskInputDto.getDueDate());
        return task;
    }

}
