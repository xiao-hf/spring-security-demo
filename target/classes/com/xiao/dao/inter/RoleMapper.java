package com.xiao.dao.inter;

import com.xiao.dao.dto.Role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertOrUpdate(Role record);

    int insertOrUpdateSelective(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int updateBatch(@Param("list") List<Role> list);

    int batchInsert(@Param("list") List<Role> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<Role> list);
}