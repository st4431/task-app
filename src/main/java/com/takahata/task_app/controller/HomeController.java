package com.takahata.task_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // ホーム画面を表示
    @GetMapping("/home")
    public String showHome() {
        return "home";
    }
}