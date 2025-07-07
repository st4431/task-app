package com.takahata.task_app.service;

import com.takahata.task_app.entity.Task;
import com.takahata.task_app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void registerNewTask(Task task) {
        taskRepository.registerNewTask(task);
    }
}
