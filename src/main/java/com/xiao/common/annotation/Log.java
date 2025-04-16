package com.xiao.common.annotation;

import lombok.Getter;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块名称
     */
    String module() default "";

    /**
     * 操作类型
     */
    OperationType type() default OperationType.OTHER;

    /**
     * 日志内容，支持SpEL表达式
     */
    String description() default "";

    /**
     * 是否保存请求参数
     */
    boolean saveParams() default true;

    /**
     * 是否保存响应结果
     */
    boolean saveResult() default false;

    /**
     * 操作类型枚举
     */
    @Getter
    enum OperationType {
        /**
         * 查询操作
         */
        QUERY("QUERY", "查询"),

        /**
         * 新增操作
         */
        INSERT("INSERT", "新增"),

        /**
         * 修改操作
         */
        UPDATE("UPDATE", "修改"),

        /**
         * 删除操作
         */
        DELETE("DELETE", "删除"),

        /**
         * 授权操作
         */
        GRANT("GRANT", "授权"),

        /**
         * 导出操作
         */
        EXPORT("EXPORT", "导出"),

        /**
         * 导入操作
         */
        IMPORT("IMPORT", "导入"),

        /**
         * 登录操作
         */
        LOGIN("LOGIN", "登录"),

        /**
         * 退出操作
         */
        LOGOUT("LOGOUT", "退出"),

        /**
         * 强制退出
         */
        FORCE_LOGOUT("FORCE_LOGOUT", "强制退出"),

        /**
         * 生成代码
         */
        GENERATE("GENERATE", "生成代码"),

        /**
         * 清空数据
         */
        CLEAN("CLEAN", "清空数据"),

        /**
         * 审核操作
         */
        APPROVE("APPROVE", "审核"),

        /**
         * 驳回操作
         */
        REJECT("REJECT", "驳回"),

        /**
         * 下载操作
         */
        DOWNLOAD("DOWNLOAD", "下载"),

        /**
         * 上传操作
         */
        UPLOAD("UPLOAD", "上传"),

        /**
         * 备份操作
         */
        BACKUP("BACKUP", "备份"),

        /**
         * 恢复操作
         */
        RESTORE("RESTORE", "恢复"),

        /**
         * 重置密码
         */
        RESET_PASSWORD("RESET_PASSWORD", "重置密码"),

        /**
         * 修改密码
         */
        CHANGE_PASSWORD("CHANGE_PASSWORD", "修改密码"),

        /**
         * 修改状态
         */
        CHANGE_STATUS("CHANGE_STATUS", "修改状态"),

        /**
         * 分配角色
         */
        ASSIGN_ROLE("ASSIGN_ROLE", "分配角色"),

        /**
         * 分配权限
         */
        ASSIGN_PERMISSION("ASSIGN_PERMISSION", "分配权限"),

        /**
         * 配置操作
         */
        CONFIG("CONFIG", "配置"),

        /**
         * 其它操作
         */
        OTHER("OTHER", "其它");

        /**
         * 操作代码
         */
        private final String code;

        /**
         * 操作描述
         */
        private final String description;

        /**
         * 构造方法
         *
         * @param code 操作代码
         * @param description 操作描述
         */
        OperationType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * 根据操作代码获取操作类型
         *
         * @param code 操作代码
         * @return 操作类型枚举实例，如果未找到匹配的代码则返回OTHER
         */
        public static OperationType getByCode(String code) {
            if (code != null) {
                for (OperationType type : values()) {
                    if (type.getCode().equals(code)) {
                        return type;
                    }
                }
            }
            return OTHER;
        }
        
        /**
         * 根据描述推断操作类型
         *
         * @param description 操作描述文本
         * @return 操作类型枚举实例，如果未能推断则返回OTHER
         */
        public static OperationType inferFromDescription(String description) {
            if (description == null || description.isEmpty()) {
                return OTHER;
            }
            
            // 按照可能性高低排序检查关键词
            if (description.contains("查询") || description.contains("获取") || description.contains("搜索") || 
                description.contains("检索") || description.contains("列表")) {
                return QUERY;
            } else if (description.contains("新增") || description.contains("添加") || description.contains("创建")) {
                return INSERT;
            } else if (description.contains("修改") || description.contains("更新") || description.contains("编辑")) {
                return UPDATE;
            } else if (description.contains("删除") || description.contains("移除") || description.contains("清除")) {
                return DELETE;
            } else if (description.contains("授权") || description.contains("分配权限")) {
                return GRANT;
            } else if (description.contains("导出")) {
                return EXPORT;
            } else if (description.contains("导入")) {
                return IMPORT;
            } else if (description.contains("登录")) {
                return LOGIN;
            } else if (description.contains("登出") || description.contains("退出")) {
                return LOGOUT;
            } else if (description.contains("强制退出")) {
                return FORCE_LOGOUT;
            } else if (description.contains("生成代码") || description.contains("代码生成")) {
                return GENERATE;
            } else if (description.contains("清空数据")) {
                return CLEAN;
            } else if (description.contains("审核") || description.contains("审批")) {
                return APPROVE;
            } else if (description.contains("驳回") || description.contains("拒绝")) {
                return REJECT;
            } else if (description.contains("下载")) {
                return DOWNLOAD;
            } else if (description.contains("上传")) {
                return UPLOAD;
            } else if (description.contains("备份")) {
                return BACKUP;
            } else if (description.contains("恢复")) {
                return RESTORE;
            } else if (description.contains("重置密码")) {
                return RESET_PASSWORD;
            } else if (description.contains("修改密码") || description.contains("更改密码")) {
                return CHANGE_PASSWORD;
            } else if (description.contains("修改状态") || description.contains("更改状态")) {
                return CHANGE_STATUS;
            } else if (description.contains("分配角色")) {
                return ASSIGN_ROLE;
            } else if (description.contains("配置")) {
                return CONFIG;
            }
            
            return OTHER;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }

    /**
     * 登录类型枚举
     */
    @Getter
    enum LoginType {
        /**
         * 密码登录
         */
        PASSWORD("PASSWORD", "密码登录"),

        /**
         * 验证码登录
         */
        CODE("CODE", "验证码登录"),

        /**
         * 单点登录
         */
        SSO("SSO", "单点登录"),

        /**
         * 第三方登录
         */
        THIRD_PARTY("THIRD_PARTY", "第三方登录"),

        /**
         * 二维码登录
         */
        QR_CODE("QR_CODE", "二维码登录"),

        /**
         * 人脸识别登录
         */
        FACE("FACE", "人脸识别登录"),

        /**
         * 指纹登录
         */
        FINGERPRINT("FINGERPRINT", "指纹登录"),

        /**
         * 自动登录
         */
        AUTO("AUTO", "自动登录"),

        /**
         * 其他登录方式
         */
        OTHER("OTHER", "其他方式");

        /**
         * 登录类型代码
         */
        private final String code;

        /**
         * 登录类型描述
         */
        private final String description;

        /**
         * 构造方法
         *
         * @param code 登录类型代码
         * @param description 登录类型描述
         */
        LoginType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * 根据代码获取登录类型
         *
         * @param code 登录类型代码
         * @return 登录类型枚举实例，如果未找到匹配的代码则返回PASSWORD
         */
        public static LoginType getByCode(String code) {
            if (code != null) {
                for (LoginType type : values()) {
                    if (type.getCode().equals(code)) {
                        return type;
                    }
                }
            }
            return PASSWORD;
        }
        
        /**
         * 根据描述推断登录类型
         *
         * @param description 登录描述文本
         * @return 登录类型枚举实例，如果未能推断则返回PASSWORD
         */
        public static LoginType inferFromDescription(String description) {
            if (description == null || description.isEmpty()) {
                return PASSWORD;
            }
            
            if (description.contains("验证码") || description.contains("短信") || description.contains("code")) {
                return CODE;
            } else if (description.contains("单点") || description.contains("SSO") || description.contains("sso")) {
                return SSO;
            } else if (description.contains("第三方") || description.contains("微信") || 
                      description.contains("QQ") || description.contains("微博")) {
                return THIRD_PARTY;
            } else if (description.contains("二维码") || description.contains("QR") || description.contains("扫码")) {
                return QR_CODE;
            } else if (description.contains("人脸") || description.contains("face")) {
                return FACE;
            } else if (description.contains("指纹") || description.contains("fingerprint")) {
                return FINGERPRINT;
            } else if (description.contains("自动") || description.contains("auto")) {
                return AUTO;
            } else if (description.contains("密码") || description.contains("password")) {
                return PASSWORD;
            }
            
            return PASSWORD;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }
}