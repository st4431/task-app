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

    public void registerUser(String username, String rawPassword, Role role) {
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);
        userRepository.save(newUser);
    }

}
