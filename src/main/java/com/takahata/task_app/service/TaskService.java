package com.takahata.task_app.service;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    public void registerNewTask(TaskInputDto newTask) {
        taskRepository.registerNewTask(taskMapper.fromInputDtoToTask(newTask));
    }

    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    public TaskUpdateDto findTaskUpdateDtoById(int id) {
        return taskMapper.toTaskUpdateDto(taskRepository.findTaskById(id));
    }

    public void updateTask(TaskUpdateDto taskUpdateDto) {
        taskRepository.updateTask(taskMapper.fromUpdateDtoToTask(taskUpdateDto));
    }


}
