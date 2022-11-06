package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.entity.Menu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 菜单权限表(Menu)数据库访问层
 *
 * @author 大只
 * @since 2022-11-06 18:45:35
 */

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}


