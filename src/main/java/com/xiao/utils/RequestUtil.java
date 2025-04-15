package com.xiao.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * HTTP请求工具类，用于获取请求相关信息
 */
public class RequestUtil {

    /**
     * 获取客户端IP地址
     *
     * @param request HTTP请求对象
     * @return 客户端IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        
        String ip = request.getHeader("X-Forwarded-For");
        
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (StringUtils.hasText(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
    
    /**
     * 获取浏览器名称
     *
     * @param request HTTP请求对象
     * @return 浏览器名称
     */
    public static String getBrowserName(HttpServletRequest request) {
        if (request == null) {
            return "未知";
        }
        
        String userAgent = request.getHeader("User-Agent");
        return getBrowserNameFromUserAgent(userAgent);
    }
    
    /**
     * 从User-Agent字符串获取浏览器名称
     *
     * @param userAgent User-Agent字符串
     * @return 浏览器名称
     */
    private static String getBrowserNameFromUserAgent(String userAgent) {
        if (!StringUtils.hasText(userAgent)) {
            return "未知";
        }
        
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
     * 获取操作系统名称
     *
     * @param request HTTP请求对象
     * @return 操作系统名称
     */
    public static String getOperatingSystem(HttpServletRequest request) {
        if (request == null) {
            return "未知";
        }
        
        String userAgent = request.getHeader("User-Agent");
        return getOperatingSystemFromUserAgent(userAgent);
    }
    
    /**
     * 从User-Agent字符串获取操作系统名称
     *
     * @param userAgent User-Agent字符串
     * @return 操作系统名称
     */
    private static String getOperatingSystemFromUserAgent(String userAgent) {
        if (!StringUtils.hasText(userAgent)) {
            return "未知";
        }
        
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Mac OS X")) {
            return "macOS";
        } else if (userAgent.contains("Linux") && !userAgent.contains("Android")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        } else {
            return "其他操作系统";
        }
    }
    
    /**
     * 获取请求的完整URL（包含查询参数）
     *
     * @param request HTTP请求对象
     * @return 完整请求URL
     */
    public static String getFullRequestUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        
        StringBuffer url = request.getRequestURL();
        String queryString = request.getQueryString();
        
        if (StringUtils.hasText(queryString)) {
            url.append("?").append(queryString);
        }
        
        return url.toString();
    }
    
    /**
     * 获取客户端信息摘要（IP、浏览器、操作系统）
     *
     * @param request HTTP请求对象
     * @return 客户端信息摘要
     */
    public static String getClientSummary(HttpServletRequest request) {
        if (request == null) {
            return "未知客户端";
        }
        
        String ip = getIpAddress(request);
        String browser = getBrowserName(request);
        String os = getOperatingSystem(request);
        
        return String.format("IP: %s | 浏览器: %s | 操作系统: %s", ip, browser, os);
    }
    
    /**
     * 检查是否为Ajax请求
     *
     * @param request HTTP请求对象
     * @return 是否为Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    /**
     * 获取请求来源URL
     *
     * @param request HTTP请求对象
     * @return 请求来源URL，如果没有则返回空字符串
     */
    public static String getReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return StringUtils.hasText(referer) ? referer : "";
    }
    
    /**
     * 获取设备类型(Mobile/PC)
     *
     * @param request HTTP请求对象
     * @return 设备类型
     */
    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (!StringUtils.hasText(userAgent)) {
            return "未知";
        }
        
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("mobile") || userAgent.contains("android") || 
            userAgent.contains("iphone") || userAgent.contains("ipad") || 
            userAgent.contains("ipod")) {
            return "Mobile";
        } else {
            return "PC";
        }
    }
}