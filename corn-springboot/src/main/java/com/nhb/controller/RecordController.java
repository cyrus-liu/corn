package com.nhb.controller;

import com.nhb.entity.Menu;
import com.nhb.entity.Record;
import com.nhb.entity.Role;
import com.nhb.service.RecordService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * (Record)控制层
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@RestController
@RequestMapping("/record")
@Api(tags = "检测记录模块")
public class RecordController {
    @Autowired
    private RecordService recordService;


    @ApiOperation("新增检测记录")
    @PostMapping
    public Result addRecord(@RequestBody Record record) {
        record.setCreateTime(new Date());
        boolean save = recordService.save(record);

        if (save) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("查看所有检测记录")
    @GetMapping("/list")
    public Result list() {
        return recordService.selectRecordList();
    }

    @ApiOperation("根据id查询检测记录")
    @GetMapping
    public Result geRecordById(Long id) {
        Record record = recordService.getById(id);
        return Result.okResult(record);
    }
    
}

