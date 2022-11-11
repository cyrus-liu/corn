package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.entity.User;
import com.nhb.utils.Result;


/**
 * 用户表(User)服务接口
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
public interface UserService extends IService<User> {

    Result getUserMenus();

    Result userList(Integer pageNum, Integer pageSize, String keywords);
}
