package com.xiao.common.enums;

import java.util.HashMap;
import java.util.Map;


public enum RespCodeEnum {
    SUCCESS("0", "成功"),
    PARAMETER_VERIFICATION_FAILED("10001", "参数校验失败"),
    PARAMETER_ILLEGAL("10002", "输入参数非法"),
    VERIFIED("10003", "校验通过"),
    FAILURE("10000", "失败"),
    NODATA("10004","查询不到数据"),
    ERRORPARAMETER("10009","无效参数"),
    NOPERMISSION("10008","您无此权限！"),
    UNLOGIN("100011","未登录或已失效"),
    FILEUPFAIL("100012","文件上传失败"),
    RPC_FAIL("100015","调用外部接口失败"),
    MESSAGE_FAIL("100016","短信发送失败"),
    SWT_MESSAGE_FAIL("100017","短信发送失败"),
    PHOTO_UN_VALID("80102402","图片低质量"),
    ;


    private String code;
    private String desc;
    public static final Map<String, RespCodeEnum> codeMap = new HashMap<>();
    private static final Map<String, RespCodeEnum> descMap = new HashMap<>();

    public String getCode() {
        return this.code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    private void setDesc(String desc) {
        this.desc = desc;
    }

    private RespCodeEnum(String code, String desc) {
        this.setCode(code);
        this.setDesc(desc);
    }

    @Override
    public String toString() {
        return "RespCodeEnum." + this.name() + "{code=" + this.getCode() + ", desc=" + this.getDesc() + "}";
    }

    public static RespCodeEnum getEnumByCode(String code) {
        RespCodeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RespCodeEnum respCodeEnum = var1[var3];
            if (respCodeEnum.getCode().equals(code)) {
                return respCodeEnum;
            }
        }

        return null;
    }

    static {
        RespCodeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (RespCodeEnum type : arr$) {
            codeMap.put(type.getCode(), type);
            descMap.put(type.getDesc(), type);
        }

    }
}
