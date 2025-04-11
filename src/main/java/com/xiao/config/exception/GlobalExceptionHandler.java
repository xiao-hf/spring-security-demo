package com.xiao.config.exception;

import com.xiao.common.AjaxResult;
import com.xiao.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 业务异常
    @ExceptionHandler(BusinessException.class)
    public AjaxResult<String> handleBusinessException(BusinessException exception) {
        return AjaxResult.error(exception.getMessage());
    }

    // 校验异常
    @ExceptionHandler(BindException.class)
    public AjaxResult<String> handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

}
