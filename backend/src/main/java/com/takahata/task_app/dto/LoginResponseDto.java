package com.takahata.task_app.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private long id;
    private String username;
    private String role;
}
