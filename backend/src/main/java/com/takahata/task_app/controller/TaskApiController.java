package com.takahata.task_app.controller;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskResponseDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.mapper.TaskMapper;
import com.takahata.task_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskApiController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAll() {
        List<Task> taskList = taskService.findAll();
        List<TaskResponseDto> taskResponseDtoList = taskList.stream()
                .map(taskMapper::toTaskResponseDto)
                .toList();
        return ResponseEntity.ok(taskResponseDtoList);
    }

    @PostMapping
    //@RequestBodyはリクエストの本文に書かれるJSONデータをTaskInputDtoオブジェクトに変換する
    public ResponseEntity<TaskResponseDto> createTask(@Validated @RequestBody TaskInputDto taskInputDto) {
        Task task = taskService.createTask(taskInputDto);
        TaskResponseDto taskResponseDto = taskMapper.toTaskResponseDto(task);
        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }

    @PutMapping
    public void updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        taskService.updateTask(taskUpdateDto);
    }
}
