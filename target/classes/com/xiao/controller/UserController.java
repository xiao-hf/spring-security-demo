package com.xiao.controller;

import com.xiao.common.AjaxResult;
import com.xiao.common.annotation.Log;
import com.xiao.common.dto.UserDto;
import com.xiao.http.req.ReqLogin;
import com.xiao.service.UserService;
import com.xiao.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/test")
    @Log(module = "测试", type = Log.OperationType.OTHER, saveParams = true, saveResult = true)
    public AjaxResult<String> test() {
        return AjaxResult.success("成功访问!");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Log(module = "测试", type = Log.OperationType.OTHER, saveParams = true, saveResult = true)
    public AjaxResult<String> admin() {
        return AjaxResult.success("ADMIN认证成功!");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public AjaxResult<String> user() {
        return AjaxResult.success("USER认证成功!");
    }

    @GetMapping("/getLoginUser")
    public AjaxResult<UserDto> getLoginUser() {
        return AjaxResult.success(SecurityUtil.getUser());
    }

    @Operation(summary = "生成验证码")
    @GetMapping("/getCaptcha/{phone}")
    public AjaxResult<String> geneCode(@PathVariable String phone) {
        return userService.geneCode(phone);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    @Log(module = "登录")
    public AjaxResult<String> login(@RequestBody @Valid ReqLogin req) {
        return userService.login(req);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    @Log(module = "登出")
    public AjaxResult<String> logout() {
        return userService.logout();
    }
}
