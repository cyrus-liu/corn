package com.nhb.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.dto.AddUserDto;
import com.nhb.dto.UpdateUserDto;
import com.nhb.entity.*;
import com.nhb.mapper.UserMapper;
import com.nhb.mapper.UserRoleMapper;
import com.nhb.service.UserService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.vo.PageVo;
import com.nhb.vo.UpdateUserVo;
import com.nhb.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Result getUserMenus() {
        //获取用户id
        long loginIdAsLong = StpUtil.getLoginIdAsLong();

        //查询该用户拥有的菜单
        List<Menu> userMenuList = getBaseMapper().selectUserByMenus(loginIdAsLong);

        //安全校验
        if (userMenuList.size() == 0) {
            return Result.errorResult(400, "没有菜单,无法生成路由组件");
        }

        List<Menu> menuTree = builderMenuTree(userMenuList);
        return Result.okResult(menuTree);
    }

    @Override
    public Result userList(Integer pageNum, Integer pageSize, String keywords) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(Objects.nonNull(keywords), User::getUserName, keywords);

        //分页
        Page<User> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, queryWrapper);
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return Result.okResult(pageVo);
    }

    @Override
    public Result addUser(AddUserDto addUserDto) {
        //新增用户
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        //加密
        String newPassword = SaSecureUtil.md5(user.getPassword());
        user.setPassword(newPassword);
        save(user);

        //根据新增角色返回的id，批量删除对应的菜单
        Long id = user.getId();
        userRoleMapper.deleteById(id);

        //将选择的角色分配给用户
        List<Long> roleIds = addUserDto.getRoleIds();

        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole(id, roleId);
            userRoleMapper.insert(userRole);
        }

        return Result.okResult();
    }

    @Override
    public Result geUserById(Long id) {
        User user = getById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        List<Long> roles = getBaseMapper().selectByRoles(id);
        userVo.setRoleIds(roles);
        return Result.okResult(userVo);
    }

    @Override
    public Result updateUser(UpdateUserDto updateUserDto) {
        //修改用户
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        //加密
        String newPassword = SaSecureUtil.md5(user.getPassword());
        user.setPassword(newPassword);
        updateById(user);

        //批量删除对应的角色
        Long id = user.getId();
        userRoleMapper.deleteById(id);

        //将选择的角色分配给用户
        List<Long> roleIds = updateUserDto.getRoleIds();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole(id, roleId);
            userRoleMapper.insert(userRole);
        }

        return Result.okResult();
    }


    /**
     * @param userMenuList 用户拥有的菜单列表
     * @return List<Menu>
     */
    private List<Menu> builderMenuTree(List<Menu> userMenuList) {
        return userMenuList.stream()
                //过滤掉子菜单，留下父菜单
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> menu.setChildren(getChildren(menu, userMenuList)))
                .collect(Collectors.toList());
    }

    /**
     * @param menu 父菜单
     * @param userMenuList 用户拥有的菜单列表
     * @return List<Menu>
     */
    private List<Menu> getChildren(Menu menu, List<Menu> userMenuList) {
        return userMenuList.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, userMenuList)))
                .collect(Collectors.toList());
    }
}
