package com.takahata.task_app.mapper;

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
        registerResponseDto.setRole(user.getRole().name());
        return registerResponseDto;
    }
}
