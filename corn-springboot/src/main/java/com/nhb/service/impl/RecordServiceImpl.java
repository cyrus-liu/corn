package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Record;
import com.nhb.mapper.RecordMapper;
import com.nhb.service.RecordService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.vo.RecordVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Record)表服务实现类
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Override
    public Result selectRecordList() {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getStatus,"0");
        List<Record> list = list(queryWrapper);
        List<RecordVo> recordVos = BeanCopyUtils.copyBeanList(list, RecordVo.class);
        return Result.okResult(recordVos);
    }


}
