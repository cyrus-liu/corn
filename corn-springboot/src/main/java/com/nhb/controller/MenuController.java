package com.nhb.controller;

import com.nhb.entity.Menu;
import com.nhb.service.MenuService;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单权限表(Menu)控制层
 * @author 大只
 * @since 2022-11-06 18:45:35
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查看所有菜单")
    @GetMapping("/list")
    public Result list(String keywords){
        return menuService.selectMenuPage(keywords);
    }

    @ApiOperation("新增菜单")
    @PostMapping
    public Result addMenu(@RequestBody Menu menu){
        System.out.println(menu);
        return null;
    }
}

