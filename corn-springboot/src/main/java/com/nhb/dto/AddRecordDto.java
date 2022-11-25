package com.nhb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author 大只
 * @date 2022-11-23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRecordDto {

    //检测名称
    private String name;
    //图片地址
    private String imgUrl;
    //备注
    private String remark;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //识别结果
    @NotEmpty(message = "没有识别结果，不能提交")
    private String resultName;
    //相似度
    @NotEmpty(message = "没有识别结果，不能提交")
    private String resultValue;
    //记录状态 0通过 1未通过
    private String status;
}
