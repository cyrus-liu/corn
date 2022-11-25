package com.nhb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nhb.error.SystemException;
import com.nhb.service.UploadService;
import com.nhb.utils.AppHttpCodeEnum;
import com.nhb.utils.QCloudCosUtils;
import com.nhb.utils.Result;
import com.nhb.vo.AiInfoVo;
import com.nhb.vo.AiResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author 大只
 * @date 2022-11-09
 */
@Service
public class OssUploadService implements UploadService {
    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.aiUrl}")
    private String aiUrl;

    @Override
    public Result uploadAiImg(MultipartFile file) {

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();

        //过滤png格式的图片
        if (originalFilename.endsWith(".png")) {
            throw new SystemException(AppHttpCodeEnum.NO_PNG);
        }


        //存储图片至腾讯对象存储
        String upload = qCloudCosUtils.upload(file);

        //转发图片url给AI服务器
        AiResultVo resultVo = PostData(upload);

        //非空判断
        if (Objects.isNull(resultVo)) {
            throw new SystemException(AppHttpCodeEnum.AI_ERROR);
        }

        //判断响应状态
        if(resultVo.getStatus() != 200){
            throw new SystemException(AppHttpCodeEnum.AI_ERROR);
        }

        //响应结果
        Object data = resultVo.getData();

        //封装vo
        AiInfoVo aiInfoVo = new AiInfoVo(data, upload);
        return Result.okResult(aiInfoVo);
    }

    public AiResultVo PostData(String imgUrl) {
        JSONObject postData = new JSONObject();
        //入参
        postData.put("img_url", imgUrl);
        //请求
        return restTemplate.postForObject(aiUrl, postData, AiResultVo.class);
    }
}
