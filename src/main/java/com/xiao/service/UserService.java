package com.xiao.service;

import com.xiao.common.AjaxResult;
import com.xiao.http.req.ReqLogin;

public interface UserService {

    AjaxResult<String> geneCode(String phone);

    AjaxResult<String> login(ReqLogin req);
}
