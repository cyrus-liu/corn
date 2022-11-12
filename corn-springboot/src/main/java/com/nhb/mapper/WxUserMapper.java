package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * (WxUser)表数据库访问层
 *
 * @author 大只
 * @since 2022-11-12 16:27:16
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

}


