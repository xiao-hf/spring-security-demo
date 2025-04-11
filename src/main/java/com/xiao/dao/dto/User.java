package com.xiao.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息表
 */
@Schema(description = "用户信息表")
@Data
public class User {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private String userNo;

    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    private String userName;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String userPhone;

    /**
     * 所属单位编号
     */
    @Schema(description = "所属单位编号")
    private String userUnitNo;

    /**
     * 是否启用(1:启用,0:禁用)
     */
    @Schema(description = "是否启用(1:启用,0:禁用)")
    private Integer userEnable;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createDate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateDate;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 最近登录时间
     */
    @Schema(description = "最近登录时间")
    private Date loginTime;

    /**
     * 是否删除(1:已删除,0:未删除)
     */
    @Schema(description = "是否删除(1:已删除,0:未删除)")
    private Integer isDeleted;

    /**
     * 战队ID
     */
    @Schema(description = "战队ID")
    private Long teamId;

    /**
     * 单位ID
     */
    @Schema(description = "单位ID")
    private Long unitId;
}