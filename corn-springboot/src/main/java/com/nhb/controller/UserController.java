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
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody @Validated UserLoginDto userLoginDto) {
        return loginService.login(userLoginDto);
    }

    //TODO 需要做鉴权登录之后才能访问
    @ApiOperation(value = "获取用户拥有的菜单")
    @GetMapping("/menus")
    public Result getUserMenus(){
        return userService.getUserMenus();
    }
}

