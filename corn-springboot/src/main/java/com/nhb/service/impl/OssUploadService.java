package com.nhb.service.impl;

import com.nhb.service.UploadService;
import com.nhb.utils.HttpRestUtils;
import com.nhb.utils.QCloudCosUtils;
import com.nhb.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 大只
 * @date 2022-11-09
 */
@Service
public class OssUploadService implements UploadService {
    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    @Override
    public Result uploadImg(MultipartFile file) {
        //存储图片至腾讯对象存储
        String upload = qCloudCosUtils.upload(file);
        //转发图片url给AI服务器
        PostData(upload);

        return null;
    }

    public String PostData(String imgUrl) {
        try {
            //api url地址
            String url = "";
            //post请求
            HttpMethod method = HttpMethod.POST;
            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("img_url", imgUrl);

            //发送http请求并返回结果
            String result = HttpRestUtils.post(url, params);
            System.out.print("接收反馈：" + result);
            return result;
        } catch (Exception e) {
            System.out.println("------------- " + this.getClass().toString() + ".PostData() : 出现异常 Exception -------------");
            System.out.println(e.getMessage());
            return "";
        }
    }
}
