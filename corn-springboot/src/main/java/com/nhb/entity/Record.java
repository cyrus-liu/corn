package com.nhb.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 检测记录表(Record)表实体类
 *
 * @author 大只
 * @since 2022-11-11 10:02:08
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
    //识别结果
    private String resultName;
    //相似度
    private String resultValue;
    //记录状态 0通过 1未通过
    private String status;


    //创建者

    private String createBy;

    //创建时间

    private Date createTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}


