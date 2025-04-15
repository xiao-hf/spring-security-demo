package com.xiao.service.impl;

import com.xiao.dao.dto.SysLoginLog;
import com.xiao.dao.dto.SysOperationLog;
import com.xiao.dao.inter.SysLoginLogMapper;
import com.xiao.dao.inter.SysOperationLogMapper;
import com.xiao.service.LogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    SysOperationLogMapper operationLogMapper;

    @Resource
    SysLoginLogMapper loginLogMapper;

    @Override
    public void saveOperationLog(SysOperationLog log) {
        log.setCreateTime(new Date());
        operationLogMapper.insert(log);
    }

    @Override
    public void saveLoginLog(SysLoginLog log) {
        log.setCreateTime(new Date());
        loginLogMapper.insert(log);
    }
}
