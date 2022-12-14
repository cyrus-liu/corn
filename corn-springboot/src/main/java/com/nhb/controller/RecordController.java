package com.nhb.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nhb.dto.AddRecordDto;
import com.nhb.dto.UpdateRoleDto;
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
import org.springframework.validation.annotation.Validated;
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
    public Result addRecord(@RequestBody @Validated AddRecordDto addRecordDto) {
        return recordService.addRecord(addRecordDto);
    }

    @ApiOperation("查看正常的取样记录")
    @GetMapping("/list")
    public Result list() {
        return recordService.selectRecordList();
    }

    @ApiOperation("查看所有取样记录")
    @SaCheckLogin
    @SaCheckPermission("record::query")
    @GetMapping("/sys/list")
    public Result list(Integer pageNum, Integer pageSize, String keywords, String status) {
        return recordService.recordList(pageNum, pageSize, keywords, status);
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

    @ApiOperation("修改取样记录")
    @SaCheckPermission("record::put")
    @PutMapping
    public Result updateRecord(@RequestBody Record record) {
        if (recordService.updateById(record)) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @ApiOperation("删除取样记录")
    @SaCheckLogin
    @SaCheckPermission("record::delete")
    @DeleteMapping("/{ids}")
    public Result deleteRecord(@PathVariable List<Long> ids) {

        if (recordService.removeByIds(ids)) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

}

