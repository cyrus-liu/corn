package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.dto.AddRoleDto;
import com.nhb.dto.UpdateRoleDto;
import com.nhb.entity.Role;
import com.nhb.entity.RoleMenu;
import com.nhb.mapper.RoleMapper;
import com.nhb.service.RoleMenuService;
import com.nhb.service.RoleService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.utils.SysConstant;
import com.nhb.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public Result roleList(Integer pageNum, Integer pageSize, String keywords) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(Objects.nonNull(keywords), Role::getRoleName, keywords);
        //排序
        queryWrapper.orderByAsc(Role::getRoleSort);
        //分页
        Page<Role> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, queryWrapper);
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return Result.okResult(pageVo);
    }

    @Override
    public Result addRole(AddRoleDto addRoleDto) {
        //新增角色
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);

        //根据新增角色返回的id，批量删除对应的菜单
        Long id = role.getId();
        roleMenuService.removeById(id);

        //将选择的菜单分配给角色
        List<Long> menuIds = addRoleDto.getMenuIds();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu(id, menuId);
            roleMenuService.save(roleMenu);
        }

        return Result.okResult();
    }

    @Override
    public Result updateRole(UpdateRoleDto updateRoleDto) {
        //修改角色
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);

        //批量删除对应的菜单
        Long id = role.getId();
        roleMenuService.removeById(id);

        //将选择的菜单分配给角色
        List<Long> menuIds = updateRoleDto.getMenuIds();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu(id, menuId);
            roleMenuService.save(roleMenu);
        }

        return Result.okResult();
    }
}
