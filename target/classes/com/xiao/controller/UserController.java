package com.xiao.controller;

import com.xiao.common.AjaxResult;
import com.xiao.http.req.ReqLogin;
import com.xiao.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Operation(summary = "生成验证码")
    @GetMapping("/getCaptcha/{phone}")
    public AjaxResult<String> geneCode(@PathVariable String phone) {
        return userService.geneCode(phone);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public AjaxResult<String> login(@RequestBody @Valid ReqLogin req) {
        return userService.login(req);
    }
}
