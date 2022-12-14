package com.nhb.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.dto.AddRecordDto;
import com.nhb.entity.Record;
import com.nhb.entity.User;
import com.nhb.error.SystemException;
import com.nhb.mapper.RecordMapper;
import com.nhb.service.RecordService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.utils.SysConstant;
import com.nhb.vo.MyRecordVo;
import com.nhb.vo.PageVo;
import com.nhb.vo.RecordVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        queryWrapper.eq(Record::getStatus, SysConstant.RECORD_STATUS_NORMAL);
        List<Record> list = list(queryWrapper);
        List<RecordVo> recordVos = BeanCopyUtils.copyBeanList(list, RecordVo.class);
        return Result.okResult(recordVos);
    }

    @Override
    public Result addRecord(AddRecordDto addRecordDto) {

        Record record = BeanCopyUtils.copyBean(addRecordDto, Record.class);

        //检查地理位置是否存在
        Record one = getOne(new QueryWrapper<Record>().lambda()
                .eq(Record::getLongitude, record.getLongitude())
                .eq(Record::getLatitude, record.getLatitude())
        );

        if (Objects.nonNull(one)) {
            throw new SystemException(AppHttpCodeEnum.LOCATION_REPETITION);
        }

        record.setCreateBy(StpUtil.getLoginIdAsString());
        record.setCreateTime(new Date());

        //插入记录
        boolean save = save(record);

        if (save) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result geRecordByUser() {
        String openId = StpUtil.getLoginIdAsString();

        List<Record> list = list(new QueryWrapper<Record>().lambda()
                .eq(Record::getCreateBy, openId)
        );

        List<MyRecordVo> myRecordVos = BeanCopyUtils.copyBeanList(list, MyRecordVo.class);

        return Result.okResult(myRecordVos);
    }

    @Override
    public Result recordList(Integer pageNum, Integer pageSize, String keywords, String status) {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(Objects.nonNull(keywords), Record::getName, keywords);
        queryWrapper.like(Objects.nonNull(status), Record::getStatus, status);

        //分页
        Page<Record> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, queryWrapper);
        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return Result.okResult(pageVo);
    }


}
