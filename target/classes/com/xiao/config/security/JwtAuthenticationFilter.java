package com.xiao.config.security;

import java.io.IOException;
import java.util.*;
import cn.hutool.core.text.AntPathMatcher;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.common.constants.RedisPrefix;
import com.xiao.common.dto.UserDto;
import com.xiao.exception.BusinessException;
import com.xiao.utils.JwtUtil;
import com.xiao.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // 不需要进行JWT认证的路径
    private static final Set<String> excludedPaths = new HashSet<>(Arrays.asList("/doc.html",
            "/user/getCaptcha/**", //获取验证码
            "/user/login/**", //登录

            "/doc.html**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/auth/**"));

    @Resource
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 1.放行不拦截的请求
        if (shouldSkipAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // 2.排除非法请求
            String authorization = request.getHeader("Authorization");
            if (StringUtils.isEmpty(authorization)) {
                log.info("Authorization未携带");
                handleAuthenticationFailure(response, "请先登录!");
                return;
            }
            if (authorization.startsWith("Bearer "))
                authorization = authorization.substring(7);

            // 2.解析校验token
            UserDto user = JwtUtil.parseAuth(authorization);
            if (user == null) {
                log.info("Authorization不合法 值:{}", authorization);
                handleAuthenticationFailure(response, "请先登录!");
                return;
            }
            String token = user.getToken();
            String key = RedisPrefix.LOGIN_TOKEN + token;
            if (!redisUtil.contains(key)) {
                handleAuthenticationFailure(response, "登录过期, 请重新登录!");
                return;
            }
            user = (UserDto) redisUtil.get(key);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleDto().getRoleCode()));

            // 3.设置认证信息
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3.放行
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            // 处理业务异常
            handleAuthenticationFailure(response, e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            handleAuthenticationFailure(response, "认证过程发生错误: " + e.getMessage());
        }
    }

    /**
     * 处理认证失败情况
     */
    private void handleAuthenticationFailure(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("code", "302");  // 使用认证失败的错误码
        body.put("message", message);
        body.put("data", null);
        body.put("timestamp", System.currentTimeMillis());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

    /**
     * 判断当前请求是否应该跳过JWT认证
     */
    private boolean shouldSkipAuthentication(HttpServletRequest request) {
        String requestPath = request.getServletPath();

        // 检查是否匹配任何排除路径
        for (String path : excludedPaths) {
            if (pathMatcher.match(path, requestPath)) {
                return true;
            }
        }

        return false;
    }
}