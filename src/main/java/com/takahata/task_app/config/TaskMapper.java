package com.takahata.task_app.config;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskMapper {
    public Task fromInputDtoToTask(TaskInputDto taskInputDto) {
        Task task = new Task();
        mapCommonFields(
                task,
                taskInputDto.getTitle(),
                taskInputDto.getDescription(),
                taskInputDto.getTaskStatus(),
                taskInputDto.getDueDate()
        );
        return task;
    }

    public Task fromUpdateDtoToTask(TaskUpdateDto taskUpdateDto) {
        Task task = new Task();
        task.setId(taskUpdateDto.getId());
        mapCommonFields(
                task,
                taskUpdateDto.getTitle(),
                taskUpdateDto.getDescription(),
                taskUpdateDto.getTaskStatus(),
                taskUpdateDto.getDueDate()
        );
        return task;
    }

    private void mapCommonFields(
            Task task,
            String title,
            String description,
            TaskStatus taskStatus,
            LocalDate dueDate
    ) {
        task.setTitle(title);
        task.setDescription(description);
        task.setTaskStatus(taskStatus);
        task.setDueDate(dueDate);
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
