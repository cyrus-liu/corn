package com.nhb.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_menu")
public class RoleMenu {

    //角色ID INPUT:insert 前自行 set 主键值
    @TableId(value = "role_id", type = IdType.INPUT)
    private Long roleId;
    //菜单ID
    private Long menuId;



}


