package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.nhb.dto.UpdateRoleDto;
import com.nhb.dto.WxUserLoginDto;
import com.nhb.entity.Role;
import com.nhb.entity.WxUser;
import com.nhb.service.WxUserService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * (WxUser)控制层
 * @author 大只
 * @since 2022-11-12 16:27:16
 */
@RestController
@RequestMapping("/wxUser")
@Api(tags = "微信用户模块")
public class WxUserController {

    @Autowired
    private WxUserService wxUserService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result wxUserLogin(@RequestBody WxUserLoginDto wxUserLoginDto) {
        return wxUserService.wxUserLogin(wxUserLoginDto);
    }

    @SaCheckLogin
    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result wxUserLogin() {
        if(StpUtil.isLogin()){
            StpUtil.logout();
            return Result.okResult();
        }

        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("查看所有用户")
    @SaCheckLogin
    @SaCheckPermission("wx::user::query")
    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, String keywords) {
        return wxUserService.userList(pageNum, pageSize, keywords);
    }


    @ApiOperation("修改用户")
    @SaCheckLogin
    @SaCheckPermission("wx::user::put")
    @PutMapping
    public Result updateRole(@RequestBody WxUser wxUser) {
        boolean b = wxUserService.updateById(wxUser);
        if(b){
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}

