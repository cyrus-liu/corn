package com.nhb.controller;

import com.nhb.dto.UserLoginDto;
import com.nhb.entity.User;
import com.nhb.service.LoginService;
import com.nhb.service.UserService;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表(User)控制层
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private LoginService loginServiceImpl;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody @Validated UserLoginDto userLoginDto) {
        return loginServiceImpl.login(userLoginDto);
    }
}

