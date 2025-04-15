package com.xiao.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.exception.BusinessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // 设置响应状态码
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 构建自定义响应体
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", 403);
        responseBody.put("message", "权限不足，无法访问该资源");
        responseBody.put("data", null);
        responseBody.put("success", false);
        responseBody.put("path", request.getRequestURI());

        // 写入响应
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}