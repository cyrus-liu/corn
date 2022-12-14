package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.entity.Menu;
import com.nhb.entity.Role;
import com.nhb.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户表(User)数据库访问层
 *
 * @author 大只
 * @since 2022-11-06 18:45:38
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<Menu> selectUserByMenus(long loginUserId);

    List<Long> selectByRoles(Long id);
}


