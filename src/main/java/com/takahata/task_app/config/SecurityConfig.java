package com.takahata.task_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // httpオブジェクトから設定を開始し、最後にbuild()を呼び出すまで
        // セミコロン(;)は記述しません。
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // すべてのリクエストを認証なしで許可する
                        .anyRequest().permitAll()
                );

        // すべての設定が終わった後、最後にhttpオブジェクトを返します。
        return http.build();
    }
}
