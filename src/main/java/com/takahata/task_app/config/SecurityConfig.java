package com.takahata.task_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
                        // Web UIからのリクエスト（GET, POST, PUT, DELETE全て）を明示的に許可します
//                        .requestMatchers("/home", "/tasks/**").permitAll()
                        // APIエンドポイントは引き続き認証を要求します
//                        .requestMatchers("/api/**").authenticated()
                        // 上記で許可したもの以外は、すべて認証を要求する設定（安全なデフォルト）
                        .anyRequest().authenticated()
                )
                // この設定では httpBasic 認証ではなく、フォームベースのログインがデフォルトで有効になります
                .formLogin(form -> {});

        return http.build();
    }
}