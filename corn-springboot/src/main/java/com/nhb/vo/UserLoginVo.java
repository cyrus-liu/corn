package com.nhb.vo;

import com.nhb.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022/9/21 15:40
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {
    private String token;
    private String  userName;
}
