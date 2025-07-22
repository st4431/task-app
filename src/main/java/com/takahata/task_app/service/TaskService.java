package com.takahata.task_app.service;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.exception.TaskNotFoundException;
import com.takahata.task_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    //読み取り専用のメソッドであることを明示し、処理を効率化させることができる
    @Transactional(readOnly = true)
    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    public void registerNewTask(TaskInputDto newTask) {
        taskRepository.registerNewTask(taskMapper.fromInputDtoToTask(newTask));
    }

    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    //もし中身が空だったら例外を投げておく。
    public TaskUpdateDto findTaskUpdateDtoById(int id) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("ID:" + id + "のタスクが見つかりません。"));
        return taskMapper.toTaskUpdateDto(task);
    }

    public void updateTask(TaskUpdateDto taskUpdateDto) {
        //更新日時の更新
        Task updatedTask = taskMapper.fromUpdateDtoToTask(taskUpdateDto);
        updatedTask.setUpdatedAt(LocalDateTime.now());
        taskRepository.updateTask(updatedTask);
    }
}
