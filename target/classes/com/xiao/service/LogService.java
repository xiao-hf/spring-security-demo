package com.xiao.service;

import com.xiao.dao.dto.SysLoginLog;
import com.xiao.dao.dto.SysOperationLog;

public interface LogService {
    void saveOperationLog(SysOperationLog log);
    void saveLoginLog(SysLoginLog log);
}
