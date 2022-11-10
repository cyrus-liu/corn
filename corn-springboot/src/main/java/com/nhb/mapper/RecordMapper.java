package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.entity.Record;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Record)表数据库访问层
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

}


