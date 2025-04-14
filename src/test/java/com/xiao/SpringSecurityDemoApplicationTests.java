package com.xiao;

import com.xiao.dao.inter.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityDemoApplicationTests {
    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(userMapper.selectByPhoneAndIsDeleted("15286610576", true));
    }

}
