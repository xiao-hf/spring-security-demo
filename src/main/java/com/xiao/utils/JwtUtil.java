package com.xiao.utils;

import com.xiao.common.dto.UserDto;
import com.xiao.dao.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    /**
     * 密钥，不能泄露
     */
    private static final String SECRET_KEY = "xiao_spring_security_demo_secret_key";
    
    /**
     * 生成token（永久有效）
     *
     * @param user 用户对象
     * @return token字符串
     */
    public String generateToken(UserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("policeId", user.getPoliceId());
        claims.put("realName", user.getRealName());
        claims.put("unitId", user.getUnitId());
        claims.put("token", user.getToken());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                // 移除了setExpiration行，使token永久有效
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}