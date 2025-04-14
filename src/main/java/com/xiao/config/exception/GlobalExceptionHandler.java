package com.xiao.config.exception;

import com.xiao.common.AjaxResult;
import com.xiao.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 业务异常
    @ExceptionHandler(BusinessException.class)
    public AjaxResult<String> handleBusinessException(BusinessException exception) {
        log.error("业务异常：{}", exception.getMessage(), exception);
        return AjaxResult.error(exception.getMessage());
    }

    // 校验异常
    @ExceptionHandler(BindException.class)
    public AjaxResult<String> handleBindException(BindException e) {
        log.error("参数绑定异常：{}", e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    // 参数校验异常（@Valid注解）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage(), e);
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    // 空指针异常
    @ExceptionHandler(NullPointerException.class)
    public AjaxResult<String> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常：", e);
        return AjaxResult.error("系统内部错误：空指针异常");
    }

    // 数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public AjaxResult<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        log.error("数组越界异常：", e);
        return AjaxResult.error("系统内部错误：数组越界异常");
    }

    // 算术异常
    @ExceptionHandler(ArithmeticException.class)
    public AjaxResult<String> handleArithmeticException(ArithmeticException e) {
        log.error("算术异常：", e);
        return AjaxResult.error("系统内部错误：算术异常");
    }

    // 类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public AjaxResult<String> handleClassCastException(ClassCastException e) {
        log.error("类型转换异常：", e);
        return AjaxResult.error("系统内部错误：类型转换异常");
    }

    // 非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public AjaxResult<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常：{}", e.getMessage(), e);
        return AjaxResult.error("非法的参数: " + e.getMessage());
    }

    // 非法状态异常
    @ExceptionHandler(IllegalStateException.class)
    public AjaxResult<String> handleIllegalStateException(IllegalStateException e) {
        log.error("非法状态异常：{}", e.getMessage(), e);
        return AjaxResult.error("非法的状态: " + e.getMessage());
    }

    // 参数解析异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数：{}", e.getMessage(), e);
        return AjaxResult.error("缺少必要的请求参数: " + e.getParameterName());
    }

    // 参数类型不匹配
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配：{}", e.getMessage(), e);
        return AjaxResult.error("参数类型不匹配: " + e.getName());
    }

    // 请求方法不支持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public AjaxResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持的请求方法：{}", e.getMessage(), e);
        return AjaxResult.error("不支持的请求方法: " + e.getMethod());
    }

    // 请求体解析异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体解析异常：{}", e.getMessage(), e);
        return AjaxResult.error("请求体格式错误");
    }

    // 文件上传大小超限
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("上传文件大小超限：{}", e.getMessage(), e);
        return AjaxResult.error("上传文件大小超过限制");
    }

    // 数据库访问异常
    @ExceptionHandler(DataAccessException.class)
    public AjaxResult<String> handleDataAccessException(DataAccessException e) {
        log.error("数据库访问异常：", e);
        return AjaxResult.error("数据库操作失败");
    }

    // SQL异常
    @ExceptionHandler(SQLException.class)
    public AjaxResult<String> handleSQLException(SQLException e) {
        log.error("SQL异常：", e);
        return AjaxResult.error("数据库操作异常");
    }

    // IO异常
    @ExceptionHandler(IOException.class)
    public AjaxResult<String> handleIOException(IOException e) {
        log.error("IO异常：", e);
        return AjaxResult.error("文件读写异常");
    }

    // 超时异常
    @ExceptionHandler(TimeoutException.class)
    public AjaxResult<String> handleTimeoutException(TimeoutException e) {
        log.error("操作超时：", e);
        return AjaxResult.error("操作超时，请稍后重试");
    }

    // 权限不足异常
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxResult<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足：{}", e.getMessage(), e);
        return AjaxResult.error(String.valueOf(HttpStatus.FORBIDDEN.value()), "权限不足，无法访问该资源");
    }

    // 兜底异常处理
    @ExceptionHandler(Exception.class)
    public AjaxResult<String> handleException(Exception e) {
        log.error("未知异常：", e);
        return AjaxResult.error("系统内部错误，请联系管理员");
    }
}