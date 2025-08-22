package com.takahata.task_app.controller;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskApiController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> findAllTasks() {
        return taskService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@RequestBodyはリクエストの本文に書かれるJSONデータをTaskInputDtoオブジェクトに変換する
    public void registerNewTask(@Validated @RequestBody TaskInputDto taskInputDto) {
        taskService.registerNewTask(taskInputDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }


    @PutMapping("/{id}")
    public void updateTask(@PathVariable long id,
                           @Validated @RequestBody TaskUpdateDto taskUpdateDto) {
        taskUpdateDto.setId(id);
        taskService.updateTask(taskUpdateDto);
    }
}
