package com.nhb.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 大只
 * @date 2022-11-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto implements Serializable {
    //用户名
    @NotEmpty(message = "用户名必填")
    private String userName;

    //密码
    @NotEmpty(message = "密码必填")
    private String password;
}
