package com.nhb;

import cn.dev33.satoken.stp.StpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CornSpringbootApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        StpUtil.logout(1L);
    }

    @Test
    public void RestTemplateTestPost() {
//        String url = "http://120.48.58.130:8080/predict/";
//
//        JSONObject postData = new JSONObject();
//
//        //入参
//        postData.put("img_url","https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/images%2Fv2-503793a901560aa558a4c75200e9e221_1440w.jpeg");
//
//        //请求
//        ResultVo resultVo = restTemplate.postForObject(url, postData, ResultVo.class);


    }

}
