package com.takahata.task_app.controller;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/display")
    public String findAllTasks(Model model) {
        List<Task> taskList = taskService.findAllTasks();
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
    public String deleteTask(@PathVariable(name = "id") int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/display";
    }

    @GetMapping("/update/{id}")
    public String updateTask(Model model, @PathVariable(name = "id") int id) {
        Task foundTask = taskService.findTaskById(id);
        model.addAttribute("foundTask", foundTask);

        return "update-task";
    }


}
