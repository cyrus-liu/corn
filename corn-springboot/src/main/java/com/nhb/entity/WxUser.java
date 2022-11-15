package com.nhb.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (WxUser)表实体类
 *
 * @author 大只
 * @since 2022-11-12 16:27:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_user")
public class WxUser {

    private Integer id;

    //昵称
    private String nickName;
    //头像
    private String avatarUrl;
    //微信开放id

    @TableId
    private String openId;

    //账号状态（0正常 1停用）
    private String status;

    //创建时间
    private Date createTime;
}


