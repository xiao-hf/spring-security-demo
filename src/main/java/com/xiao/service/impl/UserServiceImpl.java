package com.xiao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.sql.visitor.functions.If;
import com.xiao.common.AjaxResult;
import com.xiao.common.constants.RedisPrefix;
import com.xiao.common.dto.RoleDto;
import com.xiao.common.dto.UserDto;
import com.xiao.dao.dto.*;
import com.xiao.dao.inter.*;
import com.xiao.exception.BusinessException;
import com.xiao.http.req.ReqLogin;
import com.xiao.service.UserService;
import com.xiao.utils.JwtUtil;
import com.xiao.utils.MyUtil;
import com.xiao.utils.RedisUtil;
import com.xiao.utils.SecurityUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    UserMapper userMapper;

    @Resource
    RedisUtil redisUtil;

    @Resource
    RoleMapper roleMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public AjaxResult<String> geneCode(String phone) {
        List<User> users = userMapper.selectByPhoneAndIsDeleted(phone, false);
        if (users.isEmpty())
            throw new BusinessException("用户不存在!");
        String code = MyUtil.randomNumStr(6);
        redisUtil.set(RedisPrefix.LOGIN_CODE + phone, code,5, TimeUnit.MINUTES);

        // todo 发送验证码手机短信...

        log.info("手机号 {} 用户 验证码:{}", phone, code);
        return AjaxResult.success("验证码已生成, 有效期5分钟!");
    }

    @Override
    public AjaxResult<String> login(ReqLogin req) {
        Date now = new Date();
        int type = Integer.parseInt(req.getType());
        String phone = req.getPhone();
        String code = req.getCode();
        List<User> users = userMapper.selectByPhoneAndIsDeleted(phone, false);
        // 1.排除错误情况
        if (users.isEmpty())
            return AjaxResult.error("用户不存在!");
        User user = users.get(0);
        Long userId = user.getId();
        if (!user.getEnable())
            return AjaxResult.error("用户未启用!");
        if (type == 1 && !code.equals(user.getPassword())) // 密码登录
            return AjaxResult.error("密码错误!");
        else if (type == 2) {
            String key = RedisPrefix.LOGIN_CODE + phone;
            if (!redisUtil.contains(key))
                return AjaxResult.error("验证码已失效!");
            if (!code.equals(redisUtil.get(key)))
                return AjaxResult.error("验证码错误!");
        }
        // 2.封装UserDto存入redis
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(userId);
        if (userRoles.isEmpty())
            return AjaxResult.error("该用户未分配角色!");
        UserRole userRole = userRoles.get(0);
        Long roleId = userRole.getRoleId();
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (role == null)
            return AjaxResult.error("该用户角色不存在!");
        RoleDto roleDto = BeanUtil.copyProperties(role, RoleDto.class);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByRoleId(roleId);
        List<Permission> permissions = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            Long permissionId = rolePermission.getPermissionId();
            Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
            if (permission != null)
                permissions.add(permission);
        }
        roleDto.setPermissions(permissions);
        userDto.setRoleDto(roleDto);
        String token = IdUtil.randomUUID();
        userDto.setToken(token);
        // 3.更新数据库user token和登录时间
        user.setToken(token);
        user.setLastLoginTime(now);
        userMapper.updateByPrimaryKeySelective(user);
        // 4.生成token   authorization->token->UserDto
        String authorization = JwtUtil.geneAuth(userDto);
        String key = RedisPrefix.LOGIN_TOKEN + token;
        redisUtil.set(key, userDto);
        // 5.设置UserDto到security上下文
        SecurityUtil.setUser(userDto);
        return AjaxResult.success(authorization);
    }

    @Override
    public AjaxResult<String> logout() {
        String token = SecurityUtil.getToken();
        String key = RedisPrefix.LOGIN_TOKEN + token;
        redisUtil.del(key);
        return AjaxResult.success("登出成功!");
    }
}
