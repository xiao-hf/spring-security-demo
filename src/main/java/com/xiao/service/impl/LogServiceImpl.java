package com.xiao.service.impl;

import com.xiao.dao.dto.SysLog;
import com.xiao.dao.inter.SysLogMapper;
import com.xiao.service.LogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    SysLogMapper logMapper;

    @Override
    public void saveSysLog(SysLog sysLog) {
        Date now = new Date();
        sysLog.setCreateTime(now);
        logMapper.insert(sysLog);
    }
}
