package com.xiao.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.xiao.common.enums.RespCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 *
 **/
@Setter
@Getter
@Schema(description = "响应结果")
public class AjaxResult<T> implements Serializable {
    @Schema(description = "调用是否成功")
    @JSONField(ordinal = 1)
    private boolean success;

    @Schema(description = "状态码", required = true)
    @JSONField(ordinal = 2)
    private String code;

    @Schema(description = "调用结果消息")
    @JSONField(ordinal = 3)
    private String message;

    @Schema(description = "成功时响应数据")
    @JSONField(ordinal = 4)
    private T data;

    @Schema(description = "时间戳", required = true, type = "Long")
    @JSONField(ordinal = 5)
    private Long timestamp;

    public static <T> AjaxResult<T> success() {
        return (AjaxResult<T>) success(null);
    }

    public static <T> AjaxResult<T> success(T data) {
        return builder().data(data).build();
    }

    public static <T> AjaxResult<T> success(String message, T data) {
        return builder().data(data).message(message).build();
    }

    public static <T> AjaxResult<T> error() {
        return builder().success(false).code(null).message(null).data((Object) null).build();
    }

    public static <T> AjaxResult<T> error(String code, String message) {
        return builder().success(false).code(code).message(message).data((Object) null).build();
    }

    public static <T> AjaxResult<T> error(String message) {
        return builder().success(false).code(RespCodeEnum.FAILURE.getCode()).message(message).data((Object) null).build();
    }

    public static <T> AjaxResult<T> error(RespCodeEnum respCodeEnum) {
        return builder().success(false).code(respCodeEnum.getCode()).message(respCodeEnum.getDesc()).data((Object) null).build();
    }

    public static <T> AjaxResult<T> error(String message, Object data) {
        return builder().success(false).code(RespCodeEnum.FAILURE.getCode()).message(message).data((Object) data).build();
    }

    public static <T> AjaxResult<T> error(String code, String message, Object data) {
        return builder().success(false).code(code).message(message).data((Object) data).build();
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    private static boolean $default$success() {
        return true;
    }

    private static String $default$code() {
        return "200";
    }

    private static String $default$message() {
        return "";
    }

    private static Long $default$timestamp() {
        return System.currentTimeMillis();
    }

    public static <T> APIResultBuilder builder() {
        return new APIResultBuilder();
    }

    public AjaxResult() {
    }

    @ConstructorProperties({"success", "code", "message", "data", "timestamp"})
    public AjaxResult(boolean success, String code, String message, T data, Long timestamp) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static class APIResultBuilder<T> {

        private boolean success$set;
        private boolean success;
        private boolean code$set;
        private String code;
        private boolean message$set;
        private String message;
        private T data;
        private boolean timestamp$set;
        private Long timestamp;

        APIResultBuilder() {
        }

        public APIResultBuilder<T> success(boolean success) {
            this.success = success;
            this.success$set = true;
            return this;
        }

        public APIResultBuilder<T> code(String code) {
            this.code = code;
            this.code$set = true;
            return this;
        }

        public APIResultBuilder<T> message(String message) {
            this.message = message;
            this.message$set = true;
            return this;
        }

        public APIResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public APIResultBuilder<T> timestamp(Long timestamp) {
            this.timestamp = timestamp;
            this.timestamp$set = true;
            return this;
        }

        public AjaxResult<T> build() {
            return new AjaxResult<T>(this.success$set ? this.success : AjaxResult.$default$success(), this.code$set ? this.code : AjaxResult.$default$code(), this.message$set ? this.message : AjaxResult.$default$message(), this.data, this.timestamp$set ? this.timestamp : AjaxResult.$default$timestamp());
        }

        @Override
        public String toString() {
            return "AjaxResult.APIResultBuilder(success=" + this.success + ", code=" + this.code + ", message=" + this.message + ", data=" + this.data + ", timestamp=" + this.timestamp + ")";
        }
    }
}
