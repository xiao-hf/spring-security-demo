package com.xiao.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 系统操作日志
 */
@Schema(description = "系统操作日志")
@Data
public class SysOperationLog {
    /**
     * 日志主键
     */
    @Schema(description = "日志主键")
    private Long id;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String module;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private String operationType;

    /**
     * 操作描述
     */
    @Schema(description = "操作描述")
    private String description;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String method;

    /**
     * 请求URL
     */
    @Schema(description = "请求URL")
    private String requestUrl;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParams;

    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    private String responseResult;

    /**
     * 操作状态（0失败 1成功）
     */
    @Schema(description = "操作状态（0失败 1成功）")
    private Integer status;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String errorMsg;

    /**
     * 操作用户ID
     */
    @Schema(description = "操作用户ID")
    private Long userId;

    /**
     * 操作用户名
     */
    @Schema(description = "操作用户名")
    private String username;

    /**
     * 操作用户IP
     */
    @Schema(description = "操作用户IP")
    private String ip;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 执行时长(毫秒)
     */
    @Schema(description = "执行时长(毫秒)")
    private Long time;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}