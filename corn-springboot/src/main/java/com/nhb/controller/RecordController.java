package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nhb.entity.Menu;
import com.nhb.entity.Record;
import com.nhb.entity.Role;
import com.nhb.entity.WxUser;
import com.nhb.service.RecordService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.Result;
import com.nhb.vo.MyRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * (Record)控制层
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@RestController
@RequestMapping("/record")
@Api(tags = "检测记录模块")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @SaCheckLogin
    @ApiOperation("新增检测记录")
    @PostMapping
    public Result addRecord(@RequestBody Record record) {
        return recordService.addRecord(record);
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

    @SaCheckLogin
    @ApiOperation("根据用户id查询检测记录")
    @GetMapping("/my")
    public Result geRecordByUser() {
        return recordService.geRecordByUser();
    }

}

