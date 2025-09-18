package com.takahata.task_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// TODO:詳細なバリデーションを追加（エラーメッセージも含む）
@Data
@NotBlank
public class LoginRequestDto {
    private String username;
    private String password;
}
