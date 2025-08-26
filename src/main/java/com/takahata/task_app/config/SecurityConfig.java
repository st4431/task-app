package com.takahata.task_app.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginProcessingUrl("/login")//ログイン画面のurl
                .loginPage("/login")
                .defaultSuccessUrl("/")//ログインが完了した後に遷移させるべき場所を示したurl
                .failureUrl("/login?error")//ログイン失敗した時に遷移させるべき場所を示したurl
                .permitAll()//
        ).logout(logout -> logout
                .logoutSuccessUrl("/")//ログアウトした後の遷移先
        ).authorizeHttpRequests(ahr -> ahr
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().requestMatchers("/login").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")//「/admin」というurlは、ADMIN（管理者）でないと遷移できませんということ
                .anyRequest().authenticated()//
                );
        return http.build();
    }
}
