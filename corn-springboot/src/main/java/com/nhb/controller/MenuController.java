package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.nhb.entity.Menu;
import com.nhb.service.MenuService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单权限表(Menu)控制层
 *
 * @author 大只
 * @since 2022-11-06 18:45:35
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
@SaCheckLogin
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查看所有菜单")
    @SaCheckPermission("menu::query")
    @GetMapping("/list")
    public Result list(String keywords) {
        return menuService.selectMenuPage(keywords);
    }

    @ApiOperation("新增菜单")
    @SaCheckPermission("menu::add")
    @PostMapping
    public Result addMenu(@RequestBody Menu menu) {
        boolean save = menuService.save(menu);
        if (save) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("根据id查询菜单")
    @SaCheckPermission("menu::query")
    @GetMapping
    public Result geMenuById(Long id) {
        Menu menu = menuService.getById(id);
        return Result.okResult(menu);
    }

    @ApiOperation("修改菜单")
    @SaCheckPermission("menu::put")
    @PutMapping
    public Result updateMenu(@RequestBody Menu menu) {
        boolean res = menuService.updateById(menu);
        if (res) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("删除菜单")
    @SaCheckPermission("menu::delete")
    @DeleteMapping
    public Result deleteMenu(Long id) {
        boolean res = menuService.removeById(id);
        if (res) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("根据角色id查询拥护的菜单")
    @SaCheckPermission("menu::query")
    @GetMapping("/role/ids")
    public Result roleMenuIds(Long id) {
        return menuService.selectRoleMenuIds(id);
    }


}

