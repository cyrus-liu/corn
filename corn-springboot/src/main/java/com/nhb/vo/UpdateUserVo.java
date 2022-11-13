package com.nhb.vo;

import com.nhb.entity.Role;
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
public class UpdateUserVo {
    private UserVo userVo;
    private List<Long> roles;
}
