package com.xiao.exception;

import lombok.Getter;

/**
 * 自定义业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误信息
     */
    private final String message;
    
    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 构造函数
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }

    /**
     * 构造函数
     * @param message 错误信息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }
    
    /**
     * 构造函数
     * @param message 错误信息
     * @param code 错误码
     * @param cause 原始异常
     */
    public BusinessException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }
}