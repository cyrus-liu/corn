package com.nhb.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 大只
 * @date 2022-11-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyRecordVo {
    @TableId
    private Integer id;

    //检测名称
    private String name;

    //图片地址
    private String imgUrl;

    //记录状态 0通过 1未通过 2审核中
    private String status;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
