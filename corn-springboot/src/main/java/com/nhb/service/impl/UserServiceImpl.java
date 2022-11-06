package com.nhb.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Menu;
import com.nhb.entity.User;
import com.nhb.mapper.UserMapper;
import com.nhb.service.UserService;
import com.nhb.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
