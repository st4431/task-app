package com.takahata.task_app.config;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task fromInputDtoToTask(TaskInputDto taskInputDto) {
        Task task = new Task();
        task.setTitle(taskInputDto.getTitle());
        task.setDescription(taskInputDto.getDescription());
        task.setTaskStatus(taskInputDto.getTaskStatus());
        task.setDueDate(taskInputDto.getDueDate());
        return task;
    }

    public Task fromUpdateDtoToTask(TaskUpdateDto taskUpdateDto) {
        Task task = new Task();
        task.setId(taskUpdateDto.getId());
        task.setTitle(taskUpdateDto.getTitle());
        task.setDescription(taskUpdateDto.getDescription());
        task.setTaskStatus(taskUpdateDto.getTaskStatus());
        task.setDueDate(taskUpdateDto.getDueDate());
        return task;
    }

    public TaskUpdateDto toTaskUpdateDto(Task task) {
        TaskUpdateDto taskUpdateDto = new TaskUpdateDto();
        taskUpdateDto.setId(task.getId());
        taskUpdateDto.setTitle(task.getTitle());
        taskUpdateDto.setDescription(task.getDescription());
        taskUpdateDto.setTaskStatus(task.getTaskStatus());
        taskUpdateDto.setDueDate(task.getDueDate());
        return taskUpdateDto;
    }

}
