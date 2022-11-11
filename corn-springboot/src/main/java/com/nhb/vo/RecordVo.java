package com.nhb.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 大只
 * @date 2022-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordVo {
    private Integer id;
    //经度
    private String longitude;
    //纬度
    private String latitude;
}
