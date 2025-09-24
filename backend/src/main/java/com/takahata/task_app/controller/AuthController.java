package com.takahata.task_app.controller;

import com.takahata.task_app.config.JwtUtils;
import com.takahata.task_app.mapper.UserMapper;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
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
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {

        // ユーザー名、パスワードの認証
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password.", e);
        }

        // ユーザー名からJWTを取得し、クライアントに返す
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

}
