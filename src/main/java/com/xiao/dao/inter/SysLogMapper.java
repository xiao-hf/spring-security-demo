package com.xiao.dao.inter;

import com.xiao.dao.dto.SysLog;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertOrUpdate(SysLog record);

    int insertOrUpdateSelective(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    int updateBatch(@Param("list") List<SysLog> list);

    int batchInsert(@Param("list") List<SysLog> list);

    int batchInsertSelectiveUseDefaultForNull(@Param("list") List<SysLog> list);
}