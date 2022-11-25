package com.nhb.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 大只
 * @date 2022-11-08
 */

@Data
public class AddRoleDto {
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //勾选的菜单id
    private List<Long> menuIds;
}
