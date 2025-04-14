package com.xiao.controller;

import com.xiao.common.AjaxResult;
import com.xiao.common.dto.UserDto;
import com.xiao.exception.BusinessException;
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

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
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
    public AjaxResult<String> login(@RequestBody @Valid ReqLogin req) {
        return userService.login(req);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public AjaxResult<String> logout() {
        return userService.logout();
    }
}
