package com.xiao.dao.inter;

import com.xiao.dao.dto.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateBatch(@Param("list") List<User> list);

    int batchInsert(@Param("list") List<User> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<User> list);
}