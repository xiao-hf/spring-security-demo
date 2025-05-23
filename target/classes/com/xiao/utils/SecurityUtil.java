package com.xiao.utils;

import com.xiao.common.dto.UserDto;
import com.xiao.exception.BusinessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUtil {

    /**
     * 手动设置当前用户到安全上下文（用于测试或特殊场景）
     * @param user 用户对象
     */
    public static void setUser(UserDto user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // 添加角色，Spring Security的角色需要"ROLE_"前缀
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleDto().getRoleCode()));

        // 创建认证对象
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        // 设置到安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 清空当前登录用户 上下文
    public static void clear() {
        SecurityContextHolder.clearContext();
    }

    /**
     * 获取当前登录用户
     * @return 当前用户，如果未登录则返回null
     */
    public static UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception e) {
            throw new BusinessException("登录过期, 请重新登陆!");
        }
        return user;
    }

    // 获取token
    public static String getToken() {
        UserDto user = getUser();
        if (user == null)
            throw new BusinessException("Security上下文异常!");
        return user.getToken();
    }
}
