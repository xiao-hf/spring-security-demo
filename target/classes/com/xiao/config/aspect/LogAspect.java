package com.xiao.config.aspect;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.common.annotation.Log;
import com.xiao.common.dto.UserDto;
import com.xiao.dao.dto.SysLoginLog;
import com.xiao.dao.dto.SysOperationLog;
import com.xiao.service.LogService;
import com.xiao.utils.RequestUtil;
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
    private LogService logService;

    @Resource
    private ObjectMapper objectMapper;

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

        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        UserDto user = null;

        // 执行目标方法
        Object result = null;
        Exception exception = null;

        try {
            result = joinPoint.proceed();
            // 获取当前登录用户
            try {
                user = SecurityUtil.getUser();
            } catch (Exception e) {
                log.warn("获取当前登录用户信息失败: {}", e.getMessage());
            }
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            // 根据模块类型决定记录登录日志还是操作日志
            if (isLoginOperation(logAnnotation)) {
                handleLoginLog(joinPoint, request, user, exception, now, logAnnotation);
            } else {
                handleOperationLog(joinPoint, request, user, result, exception, now, executionTime);
            }
        }
    }

    /**
     * 判断是否为登录操作
     */
    private boolean isLoginOperation(Log logAnnotation) {
        // 根据模块名称或操作类型判断
        String module = logAnnotation.module();
        String operationType = logAnnotation.type().name();

        return !StringUtils.isEmpty(module) &&
                (module.contains("登录") || module.contains("登出")) ||
                operationType.equals("LOGIN") || operationType.equals("LOGOUT");
    }

    /**
     * 处理登录日志
     */
    private void handleLoginLog(JoinPoint joinPoint, HttpServletRequest request, UserDto user,
                                Exception exception, Date operationTime, Log logAnnotation) {
        try {
            String module = logAnnotation.module();

            // 获取注解信息
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // 创建登录日志实体
            SysLoginLog loginLog = new SysLoginLog();
            loginLog.setLoginTime(operationTime);

            // 设置用户信息
            if (user != null) {
                loginLog.setUserId(user.getId());
                loginLog.setUsername(user.getUsername());
            } else {
                // 尝试从请求参数中获取用户名
                Map<String, Object> params = getRequestParams(joinPoint);
                if (params.containsKey("username")) {
                    loginLog.setUsername(String.valueOf(params.get("username")));
                }
            }

            // 设置登录结果
            loginLog.setStatus(exception == null ? 1 : 0);
            if (exception != null) {
                loginLog.setMsg(exception.getMessage());
            } else {
                loginLog.setMsg((!StringUtils.isEmpty(module) && module.contains("登录")) ? "登录成功" : "登出成功");
            }

            // 设置登录类型
            loginLog.setLoginType(determineLoginType(method.getName(), joinPoint.getArgs()));

            // 设置客户端信息
            if (request != null) {
                loginLog.setIp(RequestUtil.getIpAddress(request));
                loginLog.setBrowser(RequestUtil.getBrowserName(request));
                loginLog.setOs(RequestUtil.getOperatingSystem(request));
                loginLog.setDeviceType(RequestUtil.getDeviceType(request));
                loginLog.setUserAgent(request.getHeader("User-Agent"));
            }

            // 设置登录模块
            loginLog.setLoginModule(determineLoginModule(request));

            // 保存登录日志
            logService.saveLoginLog(loginLog);
        } catch (Exception e) {
            log.error("记录登录日志失败: {}", e.getMessage());
        }
    }

    /**
     * 处理操作日志
     */
    private void handleOperationLog(JoinPoint joinPoint, HttpServletRequest request, UserDto user,
                                    Object result, Exception exception, Date operationTime, long executionTime) {
        try {
            // 创建操作日志实体
            SysOperationLog operationLog = new SysOperationLog();
            operationLog.setTime(executionTime);

            // 设置用户信息
            if (user != null) {
                operationLog.setUserId(user.getId());
                operationLog.setUsername(user.getUsername());
            }

            // 设置操作结果
            operationLog.setStatus(exception == null ? 1 : 0);
            if (exception != null) {
                operationLog.setErrorMsg(exception.getMessage());
            }

            // 处理详细日志信息
            handleDetailedOperationLog(joinPoint, request, operationLog, result);

            // 保存操作日志
            logService.saveOperationLog(operationLog);
        } catch (Exception e) {
            log.error("记录操作日志失败: {}", e.getMessage());
        }
    }

    /**
     * 处理详细的操作日志信息
     */
    private void handleDetailedOperationLog(JoinPoint joinPoint, HttpServletRequest request,
                                            SysOperationLog operationLog, Object result) {
        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        // 设置操作信息
        operationLog.setModule(logAnnotation.module());
        operationLog.setOperationType(logAnnotation.type().name());

        // 解析SpEL表达式，获取操作描述
        String description = parseExpression(logAnnotation.description(), joinPoint);
        operationLog.setDescription(description);

        // 设置方法信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        operationLog.setMethod(className + "." + methodName + "()");

        // 设置请求信息
        if (request != null) {
            operationLog.setRequestUrl(request.getRequestURI());
            operationLog.setRequestMethod(request.getMethod());
            operationLog.setIp(RequestUtil.getIpAddress(request));
            operationLog.setBrowser(RequestUtil.getBrowserName(request));
            operationLog.setOs(RequestUtil.getOperatingSystem(request));
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
                    operationLog.setRequestParams(paramsJson);
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
                operationLog.setResponseResult(resultJson);
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
    private String parseExpression(String expression, JoinPoint joinPoint) {
        // 如果表达式为空或不包含SpEL表达式特征，直接返回
        if (expression == null || !expression.contains("#")) {
            return expression;
        }

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();

            if (paramNames == null || paramNames.length == 0) {
                return expression;
            }

            // 创建表达式上下文
            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            Expression exp = parser.parseExpression(expression);
            return exp.getValue(context, String.class);
        } catch (Exception e) {
            log.error("解析表达式[{}]失败: {}", expression, e.getMessage());
            return expression;
        }
    }

    /**
     * 根据方法名和参数确定登录类型
     */
    private String determineLoginType(String methodName, Object[] args) {
        // 首先检查参数中是否有ReqLogin类型
        for (Object arg : args) {
            if (arg instanceof com.xiao.http.req.ReqLogin reqLogin) {
                String loginType = reqLogin.getType();
                
                // 根据type字段判断登录方式
                if ("1".equals(loginType)) {
                    return Log.LoginType.PASSWORD.name();
                } else if ("2".equals(loginType)) {
                    return Log.LoginType.CODE.name();
                }
            }
        }
        
        // 如果没有找到ReqLogin参数或者type为空，则按原来的逻辑继续判断
        if (methodName.contains("sso") || methodName.contains("Sso")) {
            return Log.LoginType.SSO.name();
        } else if (methodName.contains("Code") || methodName.contains("code")
                || methodName.contains("Captcha") || methodName.contains("captcha")) {
            return Log.LoginType.CODE.name();
        } else if (methodName.contains("logout") || methodName.contains("Logout")) {
            return "LOGOUT";
        } else {
            return Log.LoginType.PASSWORD.name();
        }
    }

    /**
     * 确定登录模块
     */
    private String determineLoginModule(HttpServletRequest request) {
        if (request == null) {
            return "UNKNOWN";
        }

        String url = request.getRequestURI().toLowerCase();
        String userAgent = request.getHeader("User-Agent");

        if (url.contains("/admin/") || url.contains("/manage/")) {
            return "ADMIN";
        } else if (userAgent != null && (userAgent.contains("MicroMessenger") || url.contains("/wx/"))) {
            return "MINI_PROGRAM";
        } else if (RequestUtil.getDeviceType(request).equals("Mobile")) {
            return "MOBILE";
        } else if (url.contains("/api/")) {
            return "API";
        } else {
            return "PORTAL";
        }
    }
}