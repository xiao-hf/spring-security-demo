package com.xiao.http.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReqLogin {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空!")
    private String phone;

    @Schema(description = "验证码|密码")
    @NotBlank(message = "验证码|密码 不能为空")
    private String code;

    @Schema(description = "登录方式 1为密码登录 2为验证码登录")
    @Pattern(regexp = "[12]", message = "登录方式只允许为 1密码登录 2验证码登录")
    String type;

}
