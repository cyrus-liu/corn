package com.nhb.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.nhb.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义权限验证接口扩展
 *
 * @author 大只
 * @date 2022-11-14
 */

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        if (Objects.nonNull(loginId)) {
            return  menuMapper.selectUserPermission(loginId);
        }

        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        return null;
    }
}
