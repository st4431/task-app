package com.takahata.task_app.config;

import com.takahata.task_app.dto.LoginRequestDto;
import com.takahata.task_app.dto.RegisterRequestDto;
import com.takahata.task_app.entity.Role;
import com.takahata.task_app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User fromRegisterDtoToUser (RegisterRequestDto registerRequestDto) {
        User user = new User();
        mapCommonFields(user, registerRequestDto.getUsername(), registerRequestDto.getPassword());
        // バグりそう
        if (registerRequestDto.getRole().equals("USER")) {
            user.setRole(Role.USER);
        } else if (registerRequestDto.getRole().equals("ADMIN")) {
            user.setRole(Role.ADMIN);
        }
        return user;
    }

    public User fromLoginDtoToUser (LoginRequestDto loginRequestDto) {
        User user = new User();
        mapCommonFields(user, loginRequestDto.getUsername(), user.getPassword());
        return user;
    }

    public void mapCommonFields(User user, String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
    }
}
