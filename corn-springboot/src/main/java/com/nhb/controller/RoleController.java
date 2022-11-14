package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nhb.dto.AddRoleDto;
import com.nhb.dto.UpdateRoleDto;
import com.nhb.entity.Menu;
import com.nhb.entity.Role;
import com.nhb.service.RoleService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)控制层
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@RestController
@SaCheckLogin
@RequestMapping("/role")
@Api(tags = "角色模块")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("分页查看所有角色")
    @SaCheckPermission("role::query")
    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, String keywords) {
        return roleService.roleList(pageNum, pageSize, keywords);
    }

    @ApiOperation("查看所有角色")
    @SaCheckPermission("role::query")
    @GetMapping("/roles")
    public Result list() {
        List<Role> list = roleService.list();
        return Result.okResult(list);
    }

    @ApiOperation(value = "新增角色", notes = "新增角色的同时，分配菜单")
    @SaCheckPermission("role::add")
    @PostMapping
    public Result addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    @ApiOperation("根据id查询角色")
    @SaCheckPermission("role::query")
    @GetMapping
    public Result geRoleById(Long id) {
        Role role = roleService.getById(id);
        return Result.okResult(role);
    }

    @ApiOperation(value = "修改角色", notes = "修改角色的同时，修改菜单")
    @SaCheckPermission("role::put")
    @PutMapping
    public Result updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        if(Objects.isNull(updateRoleDto.getMenuIds())){
            Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
            roleService.updateById(role);
            return Result.okResult();
        }
        return roleService.updateRole(updateRoleDto);
    }

    @ApiOperation(value = "删除角色")
    @SaCheckPermission("role::delete")
    @DeleteMapping("/{ids}")
    public Result deleteRole(@PathVariable List<Long> ids) {
        boolean b = roleService.removeByIds(ids);
        if (b) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }



}

