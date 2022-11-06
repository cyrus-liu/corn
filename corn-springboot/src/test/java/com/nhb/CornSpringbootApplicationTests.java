package com.nhb;

import cn.dev33.satoken.stp.StpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CornSpringbootApplicationTests {

    @Test
    void contextLoads() {
        StpUtil.logout(1L);
    }

}
