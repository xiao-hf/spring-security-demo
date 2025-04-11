package com.xiao.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 放行 Knife4j/Swagger 相关路径
                .requestMatchers("/doc.html**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                // 放行 用户登录 验证码 相关路径
                .requestMatchers("/user/getCaptcha/**").permitAll()
                .requestMatchers("/user/login/**").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable); // 禁用CSRF保护，API项目通常不需要

        return http.build();
    }
}