package com.nhb.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Record)表实体类
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record  {
    @TableId
    private Integer id;

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
    //检测结果
    private String result;



}


