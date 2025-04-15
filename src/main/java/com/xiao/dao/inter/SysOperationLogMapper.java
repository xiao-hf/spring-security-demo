package com.xiao.dao.inter;

import com.xiao.dao.dto.SysOperationLog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysOperationLog record);

    int insertOrUpdate(SysOperationLog record);

    int insertOrUpdateSelective(SysOperationLog record);

    int insertSelective(SysOperationLog record);

    SysOperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOperationLog record);

    int updateByPrimaryKey(SysOperationLog record);

    int updateBatch(@Param("list") List<SysOperationLog> list);

    int batchInsert(@Param("list") List<SysOperationLog> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<SysOperationLog> list);
}