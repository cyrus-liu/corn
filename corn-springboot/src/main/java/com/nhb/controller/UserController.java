package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.nhb.dto.AddUserDto;
import com.nhb.dto.UpdateUserDto;
import com.nhb.dto.UserLoginDto;
import com.nhb.entity.User;
import com.nhb.error.SystemException;
import com.nhb.service.LoginService;
import com.nhb.service.UserService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)控制层
 *
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

    @ApiOperation(value = "获取用户拥有的菜单")
    @SaCheckLogin
    @GetMapping("/menus")
    public Result getUserMenus() {
        return userService.getUserMenus();
    }


    @ApiOperation("查看所有用户")
    @SaCheckLogin
    @SaCheckPermission("user::query")
    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, String keywords) {
        return userService.userList(pageNum, pageSize, keywords);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户的同时，分配角色")
    @SaCheckLogin
    @SaCheckPermission("user::add")
    @PostMapping
    public Result addRole(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @ApiOperation(value = "修改用户", notes = "修改用户的同时，修改菜单")
    @SaCheckLogin
    @SaCheckPermission("user::put")
    @PutMapping
    public Result updateUser(@RequestBody UpdateUserDto updateUserDto) {

        if (StpUtil.getLoginIdAsLong() == updateUserDto.getId()) {
            throw new SystemException(AppHttpCodeEnum.USER_ERROR);
        }

        if (Objects.isNull(updateUserDto.getRoleIds())) {
            User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
            userService.updateById(user);
            return Result.okResult();

        }
        return userService.updateUser(updateUserDto);
    }

    @ApiOperation("根据id查询用户")
    @SaCheckLogin
    @SaCheckPermission("user::query")
    @GetMapping
    public Result geUserById(Long id) {
        return userService.geUserById(id);
    }

    @ApiOperation("删除角色")
    @SaCheckLogin
    @SaCheckPermission("user::delete")
    @DeleteMapping("/{ids}")
    public Result deleteUser(@PathVariable List<Long> ids) {

        for (Long id : ids) {
            if (StpUtil.getLoginIdAsLong() == id) {
                throw new SystemException(AppHttpCodeEnum.USER_ERROR);
            }
        }


        boolean b = userService.removeByIds(ids);
        if (b) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("注销用户")
    @SaCheckLogin
    @GetMapping("/logOut")
    public Result userLogOut() {

        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return Result.okResult();
        }

        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }


}

