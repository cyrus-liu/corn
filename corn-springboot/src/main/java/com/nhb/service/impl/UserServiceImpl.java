package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.User;
import com.nhb.mapper.UserMapper;
import com.nhb.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
