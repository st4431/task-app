package com.takahata.task_app.service;

import com.takahata.task_app.dto.TaskResponseDto;
import com.takahata.task_app.mapper.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import com.takahata.task_app.exception.TaskNotFoundException;
import com.takahata.task_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    // 読み取り専用のメソッドであることを明示し、処理を効率化させられる
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task createTask(TaskInputDto newTask) {
        Task task = taskMapper.fromInputDtoToTask(newTask);
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(long id, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("ID:" + taskUpdateDto.getId() + "のタスクが見つかりません。"));
        task = taskMapper.updateTaskFromUpdateDto(task, taskUpdateDto);
        taskRepository.save(task);
        return task;
    }

    // Stream APIを習得するための練習
    public List<Task> findTasksByStatus(TaskStatus taskStatus) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getTaskStatus().equals(taskStatus))
                .toList();
    }

}
