package com.nhb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022-11-12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxLoginVo {
    private String sessionKey;
    private String openId;
}
