package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Role;
import com.nhb.mapper.RoleMapper;
import com.nhb.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表(Role)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
