/*
 Navicat Premium Dump SQL

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : spring_security_demo

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 15/04/2025 15:16:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `permission_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限内容',
  `type` int NULL DEFAULT NULL COMMENT '权限类别，url-1，other-2',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime NULL DEFAULT NULL COMMENT '插入时间',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '权限信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN', '管理员', '2025-04-11 16:01:50', '2025-04-11 16:01:50');
INSERT INTO `role` VALUES (2, 'USER', '用户', '2025-04-11 16:02:03', '2025-04-11 16:02:03');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `create_date` datetime NULL DEFAULT NULL COMMENT '插入时间',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_role_permission`(`role_id` ASC, `permission_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色权限关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `device_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型(PC/Mobile)',
  `status` tinyint NULL DEFAULT NULL COMMENT '登录状态（0失败 1成功）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `login_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'PASSWORD' COMMENT '登录类型（PASSWORD-密码登录，CODE-验证码登录，SSO-单点登录）',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理信息',
  `login_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录模块(前台/后台)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_login_time`(`login_time` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_ip`(`ip` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1, NULL, NULL, '127.0.0.1', 'Chrome', 'Windows', 'PC', 1, '登录成功', '2025-04-15 14:59:10', 'CODE', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36', 'PORTAL', '2025-04-15 14:59:10');
INSERT INTO `sys_login_log` VALUES (2, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 'PC', 1, '登录成功', '2025-04-15 15:03:46', 'CODE', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36', 'PORTAL', '2025-04-15 15:03:46');
INSERT INTO `sys_login_log` VALUES (3, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 'PC', 1, '登录成功', '2025-04-15 15:06:45', 'LOGOUT', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36', 'PORTAL', '2025-04-15 15:06:45');
INSERT INTO `sys_login_log` VALUES (4, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 'PC', 1, '登录成功', '2025-04-15 15:13:05', 'CODE', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36', 'PORTAL', '2025-04-15 15:13:06');
INSERT INTO `sys_login_log` VALUES (5, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 'PC', 1, '登出成功', '2025-04-15 15:13:15', 'LOGOUT', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36', 'PORTAL', '2025-04-15 15:13:15');

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `operation_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URL',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `response_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回结果',
  `status` tinyint NULL DEFAULT NULL COMMENT '操作状态（0失败 1成功）',
  `error_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误消息',
  `user_id` bigint NULL DEFAULT NULL COMMENT '操作用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户名',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户IP',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `time` bigint NULL DEFAULT NULL COMMENT '执行时长(毫秒)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
INSERT INTO `sys_operation_log` VALUES (7, '测试', 'OTHER', '', 'com.xiao.controller.UserController.admin()', '/user/admin', 'GET', NULL, '{\"success\":true,\"code\":\"200\",\"message\":\"\",\"data\":\"ADMIN认证成功!\",\"timestamp\":1744700185803}', 1, NULL, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 2, NULL);
INSERT INTO `sys_operation_log` VALUES (8, '测试', 'OTHER', '', 'com.xiao.controller.UserController.admin()', '/user/admin', 'GET', NULL, '{\"success\":true,\"code\":\"200\",\"message\":\"\",\"data\":\"ADMIN认证成功!\",\"timestamp\":1744700202944}', 1, NULL, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 0, NULL);
INSERT INTO `sys_operation_log` VALUES (9, '测试', 'OTHER', '', 'com.xiao.controller.UserController.admin()', '/user/admin', 'GET', NULL, '{\"success\":true,\"code\":\"200\",\"message\":\"\",\"data\":\"ADMIN认证成功!\",\"timestamp\":1744700203529}', 1, NULL, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 0, NULL);
INSERT INTO `sys_operation_log` VALUES (10, '测试', 'OTHER', '', 'com.xiao.controller.UserController.admin()', '/user/admin', 'GET', NULL, '{\"success\":true,\"code\":\"200\",\"message\":\"\",\"data\":\"ADMIN认证成功!\",\"timestamp\":1744700235383}', 1, NULL, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 0, NULL);
INSERT INTO `sys_operation_log` VALUES (11, '测试', 'OTHER', '', 'com.xiao.controller.UserController.admin()', '/user/admin', 'GET', NULL, '{\"success\":true,\"code\":\"200\",\"message\":\"\",\"data\":\"ADMIN认证成功!\",\"timestamp\":1744700235705}', 1, NULL, 1, '1', '127.0.0.1', 'Chrome', 'Windows', 0, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `police_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '警号',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '真实姓名',
  `unit_id` bigint NOT NULL COMMENT '所属部门/单位ID',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职位',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别：0-女，1-男',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像URL',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` bit(1) NOT NULL COMMENT '是否删除',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登陆成功token',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `police_id`(`police_id` ASC) USING BTREE,
  UNIQUE INDEX `idx_police_id`(`police_id` ASC) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1', '1', '1', '1', 1, '1', '1', 1, '15286610576', '1', '1', b'1', '1970-01-03 00:00:00', '2025-04-11 14:44:41', '2025-04-15 15:13:05', b'0', 'e72343f6-ce56-4fd5-8ce4-a087f967338e');
INSERT INTO `user` VALUES (2, '323', '1', '23', '1', 1, '1', '1', 1, '15286610576', '1', '1', b'0', '1970-01-03 00:00:00', '2025-04-11 16:13:25', '1970-01-02 00:00:00', b'1', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`, `user_id`) USING BTREE,
  UNIQUE INDEX `idx_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, '2025-04-11 16:18:11', NULL);

SET FOREIGN_KEY_CHECKS = 1;
