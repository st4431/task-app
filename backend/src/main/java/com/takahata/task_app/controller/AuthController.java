package com.takahata.task_app.controller;

import com.takahata.task_app.config.UserMapper;
import com.takahata.task_app.dto.LoginRequestDto;
import com.takahata.task_app.dto.LoginResponseDto;
import com.takahata.task_app.dto.RegisterRequestDto;
import com.takahata.task_app.dto.RegisterResponseDto;
import com.takahata.task_app.entity.User;
import com.takahata.task_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    // ResponseEntityを使用すると、ステータスコードとレスポンスボディ（JSONの戻り値みたいなもの）を自由に設定できる
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        User newUser = userService.registerUser(registerRequestDto.getUsername(), registerRequestDto.getPassword(), registerRequestDto.getRole());
        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        userMapper.fromUserToRegisterDto(newUser);
        return new ResponseEntity<>(registerResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        LoginResponseDto loginResponseDto = new LoginResponseDto();

        return ResponseEntity.ok("");
    }

}
