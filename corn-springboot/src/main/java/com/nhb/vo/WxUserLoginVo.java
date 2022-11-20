package com.nhb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022-11-20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUserLoginVo {
    //用户随机昵称
    private String nickName;

    private String token;
}
