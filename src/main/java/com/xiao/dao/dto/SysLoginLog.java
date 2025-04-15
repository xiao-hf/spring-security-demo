package com.xiao.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 系统登录日志
 */
@Schema(description = "系统登录日志")
@Data
public class SysLoginLog {
    /**
     * 日志ID
     */
    @Schema(description = "日志ID")
    private Long id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 登录IP
     */
    @Schema(description = "登录IP")
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
     * 设备类型(PC/Mobile)
     */
    @Schema(description = "设备类型(PC/Mobile)")
    private String deviceType;

    /**
     * 登录状态（0失败 1成功）
     */
    @Schema(description = "登录状态（0失败 1成功）")
    private Integer status;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private Date loginTime;

    /**
     * 登录类型（PASSWORD-密码登录，CODE-验证码登录，SSO-单点登录）
     */
    @Schema(description = "登录类型（PASSWORD-密码登录，CODE-验证码登录，SSO-单点登录）")
    private String loginType;

    /**
     * 用户代理信息
     */
    @Schema(description = "用户代理信息")
    private String userAgent;

    /**
     * 登录模块(前台/后台)
     */
    @Schema(description = "登录模块(前台/后台)")
    private String loginModule;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}