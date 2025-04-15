package com.xiao.dao.inter;

import com.xiao.dao.dto.SysLoginLog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    int insertOrUpdate(SysLoginLog record);

    int insertOrUpdateSelective(SysLoginLog record);

    int insertSelective(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginLog record);

    int updateByPrimaryKey(SysLoginLog record);

    int updateBatch(@Param("list") List<SysLoginLog> list);

    int batchInsert(@Param("list") List<SysLoginLog> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<SysLoginLog> list);
}