package com.takahata.task_app.service;

import com.takahata.task_app.entity.Role;
import com.takahata.task_app.entity.User;
import com.takahata.task_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User registerUser(String username, String rawPassword, String role) {
        User newUser = new User();
        String hashedPassword = passwordEncoder.encode(rawPassword);

        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(Role.USER);

//        // TODO:カスタム例外の作成、その処理の実装
//        if (role.equals("USER")) {
//            newUser.setRole(Role.USER);
//        } else if (role.equals("ADMIN")) {
//            newUser.setRole(Role.ADMIN);
//        }
        userRepository.save(newUser);
        return newUser;
    }

}

