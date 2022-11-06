package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.UserRole;
import com.nhb.mapper.UserRoleMapper;
import com.nhb.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
