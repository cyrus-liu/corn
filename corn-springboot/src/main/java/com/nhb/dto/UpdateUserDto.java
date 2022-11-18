package com.nhb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 大只
 * @date 2022-11-13
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private Long id;
    //昵称
    private String nickName;

    private String password;

    //账号状态（0正常 1停用）
    private String status;

    //勾选的角色id
    private List<Long> roleIds;
}
