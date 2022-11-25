package com.nhb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022-11-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxUserLoginDto {
    //微信临时登录凭证
    private String code;
}
