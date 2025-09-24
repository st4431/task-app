package com.takahata.task_app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private JwtAuthFilter jwtAuthFilter;

    // formLoginだとSpring Securityのみで認証を処理してしまい、Controller層にリクエストが届かないため、Rest APIには対応していない
    // SSRのみに対応している
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // REST APIではセッションは不要なので、無効化する
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )

                // JwtAuthFilter をSpring Securityのフィルターチェーンに追加
                // UsernamePasswordAuthenticationFilter の前に実行することで、
                // ID、パスワード認証の前にJWTの検証が行われるようになる
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // CSRF攻撃の対策を無効化
                // JWTを使用する場合、セッションを使わないため、CSRF対策は不要となるため
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ハッシュ化されたパスワードと入力値を比較し、認証判定を行うクラス
    // 自分で認証判定ロジックを実装することも可能だが、記述量が多くなるためよく使用されている
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
