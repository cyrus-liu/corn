package com.nhb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 大只
 * @date 2022-11-06
 */

@RestController
@RequestMapping("/test")
public class Test {
    @GetMapping
    public String test() {
        return "测试成功";
    }
}
