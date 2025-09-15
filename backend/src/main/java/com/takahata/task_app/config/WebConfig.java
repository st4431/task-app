package com.takahata.task_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api/で始まるすべてのパスを対象にする
                .allowedOrigins("http://localhost:5173") // このオリジンからのリクエストを許可する
                .allowedMethods("GET", "POST", "PUT", "DELETE") // これらのHTTPメソッドを許可する
                .allowedHeaders("*"); // すべてのヘッダーを許可する
    }
}