package com.takahata.task_app.config;

import com.takahata.task_app.dto.RegisterResponseDto;
import com.takahata.task_app.entity.Role;
import com.takahata.task_app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public RegisterResponseDto fromUserToRegisterDto(User user) {
        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        registerResponseDto.setId(user.getId());
        registerResponseDto.setUsername(user.getUsername());

        // TODO:カスタム例外の作成、その処理の実装
        if (user.getRole().equals(Role.USER)) {
            registerResponseDto.setRole("USER");
        } else if (user.getRole().equals(Role.ADMIN)) {
            registerResponseDto.setRole("ADMIN");
        }
        return registerResponseDto;
    }
}
