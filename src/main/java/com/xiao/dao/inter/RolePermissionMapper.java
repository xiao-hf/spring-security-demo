package com.xiao.dao.inter;

import com.xiao.dao.dto.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper {
    List<RolePermission> selectByRoleId(@Param("roleId")Long roleId);

    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertOrUpdate(RolePermission record);

    int insertOrUpdateSelective(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    int updateBatch(@Param("list") List<RolePermission> list);

    int batchInsert(@Param("list") List<RolePermission> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<RolePermission> list);
}