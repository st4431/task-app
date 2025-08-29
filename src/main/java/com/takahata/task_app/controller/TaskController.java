package com.takahata.task_app.controller;

import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks") // Webページ用のURLは /tasks になります
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // タスク一覧画面を表示
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "display-tasks"; // templates/display-tasks.html を表示します
    }

    // タスク登録フォーム画面を表示
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newTask", new TaskInputDto());
        return "input-task"; // templates/input-task.html を表示します
    }

    // タスク登録処理を実行
    @PostMapping("/register")
    public String registerTask(@Validated @ModelAttribute("newTask") TaskInputDto newTask, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "input-task";
        }
        taskService.registerNewTask(newTask);
        return "redirect:/tasks"; // 登録後はタスク一覧画面にリダイレクトします
    }

    // タスク更新フォーム画面を表示
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        model.addAttribute("taskUpdateDto", taskService.findTaskUpdateDtoById(id));
        return "update-task"; // templates/update-task.html を表示します
    }

    // タスク更新処理を実行
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable long id, @Validated @ModelAttribute("taskUpdateDto") TaskUpdateDto taskUpdateDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("taskUpdateDto", taskUpdateDto);
            return "update-task";
        }
        taskUpdateDto.setId(id);
        taskService.updateTask(taskUpdateDto);
        return "redirect:/tasks"; // 更新後はタスク一覧画面にリダイレクトします
    }

    // タスク削除処理を実行
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks"; // 削除後はタスク一覧画面にリダイレクトします
    }

    // トップページ（/hello）へのリンク用
    @GetMapping("/hello")
    public String hello() {
        return "hello"; // もしhello.htmlのようなトップページがあれば
    }
}