package com.xiao.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 角色权限关系表
 */
@Schema(description="角色权限关系表")
@Data
public class RolePermission {
    /**
    * 主键ID
    */
    @Schema(description="主键ID")
    private Long id;

    /**
    * 角色ID
    */
    @Schema(description="角色ID")
    private Long roleId;

    /**
    * 权限ID
    */
    @Schema(description="权限ID")
    private Long permissionId;

    /**
    * 插入时间
    */
    @Schema(description="插入时间")
    private Date createDate;

    /**
    * 更新时间
    */
    @Schema(description="更新时间")
    private Date updateDate;

    /**
    * 创建人
    */
    @Schema(description="创建人")
    private Long createUserId;

    /**
    * 修改人
    */
    @Schema(description="修改人")
    private Long updateUserId;
}