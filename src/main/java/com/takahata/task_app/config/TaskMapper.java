package com.takahata.task_app.config;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class TaskMapper {
    public Task fromInputDtoToTask(TaskInputDto taskInputDto) {
        Task task = new Task();
        // statusのnullは許されていないので、デフォルト値をここでいれいぇ
        TaskStatus status = Optional.ofNullable(taskInputDto.getTaskStatus())
                                    .orElse(TaskStatus.NOT_STARTED);
        mapCommonFields(
                task,
                taskInputDto.getTitle(),
                taskInputDto.getDescription(),
                status,
                taskInputDto.getDueDate()
        );
        return task;
    }

    public void updateTaskFromUpdateDto(Task task, TaskUpdateDto taskUpdateDto) {
        if (taskUpdateDto.getTaskStatus() == TaskStatus.NOT_STARTED) {
            taskUpdateDto.setTaskStatus(TaskStatus.COMPLETED);
        } else if (taskUpdateDto.getTaskStatus() == TaskStatus.COMPLETED) {
            taskUpdateDto.setTaskStatus(TaskStatus.NOT_STARTED);
        }
        mapCommonFields(
                task,
                taskUpdateDto.getTitle(),
                taskUpdateDto.getDescription(),
                taskUpdateDto.getTaskStatus(),
                taskUpdateDto.getDueDate()
        );
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
