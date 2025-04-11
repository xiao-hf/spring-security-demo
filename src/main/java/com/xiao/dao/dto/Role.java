package com.xiao.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 角色信息表
 */
@Schema(description = "角色信息表")
@Data
public class Role {
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色排序
     */
    @Schema(description = "角色排序")
    private Integer roleSort;

    /**
     * 角色状态(1:正常,0:停用)
     */
    @Schema(description = "角色状态(1:正常,0:停用)")
    private Integer roleStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

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
     * 是否删除(1:已删除,0:未删除)
     */
    @Schema(description = "是否删除(1:已删除,0:未删除)")
    private Integer isDeleted;
}