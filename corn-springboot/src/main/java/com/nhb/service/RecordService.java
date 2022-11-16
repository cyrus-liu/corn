package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.entity.Record;
import com.nhb.utils.Result;
import org.springframework.web.multipart.MultipartFile;


/**
 * (Record)服务接口
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
public interface RecordService extends IService<Record> {


    Result selectRecordList();

    Result addRecord(Record record);

    Result geRecordByUser();

    Result recordList(Integer pageNum, Integer pageSize, String keywords,String status);
}
