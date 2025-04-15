package com.xiao.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.xiao.common.enums.RespCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "响应结果")
public class AjaxResult<T> implements Serializable {

    @Schema(description = "调用是否成功")
    @JSONField(ordinal = 1)
    @Builder.Default
    private boolean success = true;

    @Schema(description = "状态码", required = true)
    @JSONField(ordinal = 2)
    @Builder.Default
    private String code = "200";

    @Schema(description = "调用结果消息")
    @JSONField(ordinal = 3)
    @Builder.Default
    private String message = "";

    @Schema(description = "成功时响应数据")
    @JSONField(ordinal = 4)
    private T data;

    @Schema(description = "时间戳", required = true, type = "Long")
    @JSONField(ordinal = 5)
    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    /**
     * 返回成功结果
     */
    public static <T> AjaxResult<T> success() {
        return success(null);
    }

    /**
     * 返回成功结果
     * @param data 响应数据
     */
    public static <T> AjaxResult<T> success(T data) {
        return AjaxResult.<T>builder()
                .data(data)
                .build();
    }

    /**
     * 返回成功结果
     * @param message 成功消息
     * @param data 响应数据
     */
    public static <T> AjaxResult<T> success(String message, T data) {
        return AjaxResult.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 返回默认错误结果
     */
    public static <T> AjaxResult<T> error() {
        return AjaxResult.<T>builder()
                .success(false)
                .code(RespCodeEnum.FAILURE.getCode())
                .message(RespCodeEnum.FAILURE.getDesc())
                .build();
    }

    /**
     * 返回指定错误信息的错误结果
     * @param message 错误消息
     */
    public static <T> AjaxResult<T> error(String message) {
        return AjaxResult.<T>builder()
                .success(false)
                .code(RespCodeEnum.FAILURE.getCode())
                .message(message)
                .build();
    }

    /**
     * 返回指定错误代码和错误信息的错误结果
     * @param code 错误代码
     * @param message 错误消息
     */
    public static <T> AjaxResult<T> error(String code, String message) {
        return AjaxResult.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }

    /**
     * 返回指定枚举类型的错误结果
     * @param respCodeEnum 响应码枚举
     */
    public static <T> AjaxResult<T> error(RespCodeEnum respCodeEnum) {
        return AjaxResult.<T>builder()
                .success(false)
                .code(respCodeEnum.getCode())
                .message(respCodeEnum.getDesc())
                .build();
    }

    /**
     * 返回带数据的错误结果
     * @param message 错误消息
     * @param data 错误数据
     */
    public static <T> AjaxResult<T> error(String message, T data) {
        return AjaxResult.<T>builder()
                .success(false)
                .code(RespCodeEnum.FAILURE.getCode())
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 返回指定错误代码、错误信息和数据的错误结果
     * @param code 错误代码
     * @param message 错误消息
     * @param data 错误数据
     */
    public static <T> AjaxResult<T> error(String code, String message, T data) {
        return AjaxResult.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}