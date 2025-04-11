package com.xiao.common.dto;

import com.xiao.dao.dto.Permission;
import com.xiao.dao.dto.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends Role {
    @Schema(description = "权限")
    List<Permission> permissions;
}
