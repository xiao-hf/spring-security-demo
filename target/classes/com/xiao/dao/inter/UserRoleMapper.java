package com.xiao.dao.inter;

import com.xiao.dao.dto.UserRole;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    List<UserRole> selectByUserId(@Param("userId")Long userId);

    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertOrUpdate(UserRole record);

    int insertOrUpdateSelective(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int updateBatch(@Param("list") List<UserRole> list);

    int batchInsert(@Param("list") List<UserRole> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<UserRole> list);
}