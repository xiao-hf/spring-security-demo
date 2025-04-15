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
         * -- GETTER --
         *  获取操作代码
         *
         * @return 操作代码

         */
        private final String code;

        /**
         * 操作描述
         * -- GETTER --
         *  获取操作描述
         *
         * @return 操作描述

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

        @Override
        public String toString() {
            return this.description;
        }
    }
}