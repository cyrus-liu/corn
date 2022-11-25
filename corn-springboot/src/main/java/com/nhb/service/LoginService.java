package com.nhb.service;

import com.nhb.dto.UserLoginDto;
import com.nhb.entity.User;
import com.nhb.utils.Result;

/**
 * @author 大只
 * @date 2022-11-06
 */
public interface LoginService {
    Result login(UserLoginDto userLoginDto);
}
