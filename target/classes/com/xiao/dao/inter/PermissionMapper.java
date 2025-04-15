package com.xiao.dao.inter;

import com.xiao.dao.dto.Permission;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertOrUpdate(Permission record);

    int insertOrUpdateSelective(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    int updateBatch(@Param("list") List<Permission> list);

    int batchInsert(@Param("list") List<Permission> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<Permission> list);
}