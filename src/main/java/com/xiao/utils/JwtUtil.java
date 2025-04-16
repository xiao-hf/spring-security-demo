package com.xiao.utils;

import com.xiao.common.dto.UserDto;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public static String geneAuth(UserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token", user.getToken());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                // 移除了setExpiration行，使token永久有效
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 解析jwt
    public static UserDto parseAuth(String authorization) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(authorization)
                    .getBody();

            UserDto user = new UserDto();
            user.setToken(claims.get("token", String.class));

            return user;
        } catch (Exception e) {
            // 可以记录异常日志
            // logger.error("Token解析失败", e);
            return null;
        }
    }
}