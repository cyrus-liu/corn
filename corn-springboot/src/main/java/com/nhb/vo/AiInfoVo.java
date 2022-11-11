package com.nhb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022-11-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiInfoVo {
    private AiResultVo aiResultVo;
    private String imgUrl;
}
