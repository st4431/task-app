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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/display")
    public String displayTasks(Model model) {
        List<Task> taskList = taskService.displayTasks();
        model.addAttribute("tasks", taskList);
        return "display-tasks";
    }

    @GetMapping("/register")
    public String registerNewTask(Model model) {
        model.addAttribute("taskInputDto", new TaskInputDto());
        return "input-task";
    }

    @PostMapping("/register")
    public String registerNewTask(@Validated @ModelAttribute("taskInputDto") TaskInputDto taskInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "input-task";
        }
        taskService.registerNewTask(taskMapper.toTask(taskInputDto));
        return "redirect:/tasks/display";
    }
}
