package com.takahata.task_app.service;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
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
        taskRepository.registerNewTask(taskMapper.toTask(newTask));
    }

    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    public Task findTaskById(int id) {
        return taskRepository.findTaskById(id);
    }


}
