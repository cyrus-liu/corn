package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Record;
import com.nhb.mapper.RecordMapper;
import com.nhb.service.RecordService;
import com.nhb.utils.HttpRestUtils;
import com.nhb.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

/**
 * (Record)表服务实现类
 *
 * @author 大只
 * @since 2022-11-09 14:14:32
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Override
    public Result aiImg(MultipartFile file) {

        return null;
    }



}
