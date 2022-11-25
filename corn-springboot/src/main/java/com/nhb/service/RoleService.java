package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.dto.AddRoleDto;
import com.nhb.dto.UpdateRoleDto;
import com.nhb.entity.Role;
import com.nhb.utils.Result;


/**
 * 角色信息表(Role)服务接口
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
public interface RoleService extends IService<Role> {

    Result roleList(Integer pageNum, Integer pageSize, String keywords);

    Result addRole(AddRoleDto addRoleDto);

    Result updateRole(UpdateRoleDto updateRoleDto);
}
