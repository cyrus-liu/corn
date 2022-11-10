package com.nhb.controller;

import com.nhb.entity.Record;
import com.nhb.service.RecordService;
import com.nhb.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    @ApiOperation("AI识别图片")
    @PostMapping("/ai")
    public Result aiImg(MultipartFile file){
        return recordService.aiImg(file);
    }
    
}

