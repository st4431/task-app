package com.takahata.task_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // CSSやJSなどの静的リソースへのアクセスを許可
                        .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
                        // ホーム画面とAPIへのアクセスを今は全て許可する（後で認証を追加できます）
                        .requestMatchers("/", "/home", "/tasks/**", "/api/**").permitAll()
                        // その他のリクエストは認証が必要
                        .anyRequest().authenticated()
                )
                // ログインフォームを有効にする（今回はまだ使いませんが、今後の拡張のため）
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                );
        return http.build();
    }
}