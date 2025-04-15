package com.xiao.config.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.common.annotation.Log;
import com.xiao.common.dto.UserDto;
import com.xiao.dao.dto.SysLog;
import com.xiao.dao.inter.SysLogMapper;
import com.xiao.service.LogService;
import com.xiao.utils.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志记录切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    LogService logService;

    @Resource
    ObjectMapper objectMapper;

    // 用于解析SpEL表达式
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.xiao.common.annotation.Log)")
    public void logPointCut() {
    }
    
    /**
     * 环绕通知处理
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Date now = new Date();
        long startTime = System.currentTimeMillis();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 创建日志实体
        SysLog sysLog = new SysLog();
        sysLog.setOperationTime(now);
        
        // 获取当前登录用户
        try {
            UserDto user = SecurityUtil.getUser();
            if (user != null) {
                sysLog.setUserId(user.getId());
                sysLog.setUsername(user.getUsername());
            }
        } catch (Exception e) {
            log.warn("获取当前登录用户信息失败: {}", e.getMessage());
        }
        
        // 执行目标方法
        Object result = null;
        try {
            result = joinPoint.proceed();
            sysLog.setStatus(1); // 成功
            return result;
        } catch (Exception e) {
            sysLog.setStatus(0); // 失败
            sysLog.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            sysLog.setTime(endTime - startTime);
            
            // 处理日志信息
            handleLog(joinPoint, request, sysLog, result);
            
            // 保存日志
            try {
                logService.saveSysLog(sysLog);
            } catch (Exception e) {
                log.error("保存操作日志失败: {}", e.getMessage());
            }
        }
    }
    
    /**
     * 处理日志信息
     */
    private void handleLog(JoinPoint joinPoint, HttpServletRequest request, SysLog sysLog, Object result) {
        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        
        // 设置操作信息
        sysLog.setModule(logAnnotation.module());
        sysLog.setOperationType(logAnnotation.type().name());
        
        // 解析SpEL表达式，获取操作描述
        String description = parseExpression(logAnnotation.description(), method, joinPoint.getArgs());
        sysLog.setDescription(description);
        
        // 设置方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        
        // 设置请求信息
        if (request != null) {
            sysLog.setRequestUrl(request.getRequestURI());
            sysLog.setRequestMethod(request.getMethod());
            sysLog.setIp(getIpAddress(request));
            
            // 可以添加获取浏览器和操作系统信息的方法
            String userAgent = request.getHeader("User-Agent");
            if (userAgent != null) {
                sysLog.setBrowser(getBrowserName(userAgent));
                sysLog.setOs(getOperatingSystem(userAgent));
            }
            
        }
        
        // 处理请求参数
        if (logAnnotation.saveParams()) {
            try {
                // 获取请求参数并转换为JSON
                Map<String, Object> params = getRequestParams(joinPoint);
                if (!params.isEmpty()) {
                    String paramsJson = objectMapper.writeValueAsString(params);
                    // 限制参数长度，避免过长
                    if (paramsJson.length() > 2000) {
                        paramsJson = paramsJson.substring(0, 2000) + "...";
                    }
                    sysLog.setRequestParams(paramsJson);
                }
            } catch (JsonProcessingException e) {
                log.error("处理请求参数失败: {}", e.getMessage());
            }
        }
        
        // 处理响应结果
        if (logAnnotation.saveResult() && result != null) {
            try {
                String resultJson = objectMapper.writeValueAsString(result);
                // 限制结果长度，避免过长
                if (resultJson.length() > 2000) {
                    resultJson = resultJson.substring(0, 2000) + "...";
                }
                sysLog.setResponseResult(resultJson);
            } catch (JsonProcessingException e) {
                log.error("处理响应结果失败: {}", e.getMessage());
            }
        }
    }
    
    /**
     * 获取请求参数
     */
    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>();
        
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        
        if (parameterNames != null && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                Object arg = args[i];
                
                // 过滤掉不需要记录的参数类型
                if (arg instanceof HttpServletRequest || 
                    arg instanceof HttpServletResponse || 
                    arg instanceof byte[] ||
                    arg instanceof jakarta.servlet.http.HttpSession) {
                    continue;
                }
                
                params.put(parameterNames[i], arg);
            }
        }
        
        return params;
    }
    
    /**
     * 解析SpEL表达式
     */
    private String parseExpression(String expression, Method method, Object[] args) {
        // 如果表达式为空或不包含SpEL表达式特征，直接返回
        if (expression == null || !expression.contains("#")) {
            return expression;
        }

        // 获取方法参数名
        // 直接从方法签名获取参数名称
        MethodSignature signature = null;
        try {
            signature = (MethodSignature) method.getDeclaringClass()
                    .getDeclaredMethod(method.getName(), method.getParameterTypes())
                    .getAnnotation(Aspect.class);
        } catch (Exception ignored) {
        }
        assert signature != null;
        String[] paramNames = signature.getParameterNames();
        if (paramNames == null || paramNames.length == 0) {
            return expression;
        }
        
        // 创建表达式上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        
        try {
            Expression exp = parser.parseExpression(expression);
            return exp.getValue(context, String.class);
        } catch (Exception e) {
            log.error("解析表达式[{}]失败: {}", expression, e.getMessage());
            return expression;
        }
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
    
    /**
     * 获取浏览器名称
     */
    private String getBrowserName(String userAgent) {
        if (userAgent == null) return "未知";
        
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Chrome") && !userAgent.contains("Edg")) {
            return "Chrome";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";
        } else if (userAgent.contains("Edg")) {
            return "Microsoft Edge";
        } else if (userAgent.contains("Opera") || userAgent.contains("OPR")) {
            return "Opera";
        } else {
            return "其他浏览器";
        }
    }
    
    /**
     * 获取操作系统
     */
    private String getOperatingSystem(String userAgent) {
        if (userAgent == null) return "未知";
        
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Mac OS X")) {
            return "macOS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        } else {
            return "其他操作系统";
        }
    }
}