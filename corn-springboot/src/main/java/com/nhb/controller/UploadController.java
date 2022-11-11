package com.nhb.controller;

import com.nhb.service.UploadService;
import com.nhb.utils.Result;
import com.nhb.vo.AiResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 大只
 * @date 2022-11-09
 */

@RestController
@Api("文件上传模块")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation("上传图片")
    public Result uploadAiImg(MultipartFile file){
        return uploadService.uploadAiImg(file);
    }

}
