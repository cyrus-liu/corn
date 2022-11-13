package com.nhb.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 大只
 * @date 2022-11-13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    //主键
    private Long id;

    //昵称
    private String nickName;

    //账号状态（0正常 1停用）
    private String status;

    private List<Long> roleIds;

}
