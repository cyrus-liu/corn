package com.nhb.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nhb.dto.UserLoginDto;
import com.nhb.entity.User;
import com.nhb.error.SystemException;
import com.nhb.mapper.UserMapper;
import com.nhb.service.LoginService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.utils.SysConstant;
import com.nhb.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Objects;

/**
 * @author 大只
 * @date 2022-11-06
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(UserLoginDto userLoginDto) {

        //前端账号
        String userName = userLoginDto.getUserName();
        //前端的密码
        String password = userLoginDto.getPassword();

        //查看这个账号是不是存在的
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, userName));

        if (Objects.isNull(user)) {
            //账号不存在
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        if(user.getStatus().equals(SysConstant.USER_STATE_BAN)){
            //账号停用
            throw new SystemException(AppHttpCodeEnum.USER_BAN);
        }

        //加密
        String newPassword = SaSecureUtil.md5(password);

        //对前端提交的账号和密码
        if (user.getUserName().equals(userName) && user.getPassword().equals(newPassword)) {
            //根据账号id，进行登录
            StpUtil.login(user.getId());

            //获取token
            String token = StpUtil.getTokenValue();

            //登录成功响应前端
            UserLoginVo userLoginVo = new UserLoginVo(token,user.getUserName());
            return Result.okResult(userLoginVo);
        }

        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
