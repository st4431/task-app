package com.takahata.task_app.controller;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;


    @GetMapping("/display")
    public String findAllTasks(Model model) {
        List<Task> taskList = taskService.findAll();
        model.addAttribute("tasks", taskList);
        return "display-tasks";
    }

    @GetMapping("/register")
    public String registerNewTask(Model model) {
        model.addAttribute("newTask", new TaskInputDto());
        return "input-task";
    }

    @PostMapping("/register")
    public String registerNewTask(@Validated @ModelAttribute("newTask") TaskInputDto newTask, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "input-task";
        }
        taskService.registerNewTask(newTask);
        return "redirect:/tasks/display";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/display";
    }

    @GetMapping("/update/{id}")
    public String updateTask(Model model, @PathVariable(name = "id") long id) {
        TaskUpdateDto taskUpdateDto = taskService.findTaskUpdateDtoById(id);
        model.addAttribute("taskUpdateDto", taskUpdateDto);
        return "update-task";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@Validated @ModelAttribute("taskUpdateDto") TaskUpdateDto taskUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-task";
        }
        taskService.updateTask(taskUpdateDto);
        return "redirect:/tasks/display";
    }


}
