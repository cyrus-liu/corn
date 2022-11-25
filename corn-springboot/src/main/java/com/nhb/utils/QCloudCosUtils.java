package com.nhb.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 腾讯云对象存储工具类
 * @author 大只
 * @date 2022-11-09
 */


public class QCloudCosUtils {
    //API密钥secretId
    private String secretId;
    //API密钥secretKey
    private String secretKey;
    //存储桶所属地域
    private String region;
    //存储桶空间名称
    private String bucketName;
    //存储桶访问域名
    private String url;
    //上传文件前缀路径(eg:/images/)
    private String prefix;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 上传File类型的文件
     *
     * @param file
     * @return 上传文件在存储桶的链接
     */
    public String upload(File file) {
        //生成唯一文件名
        String newFileName = generateUniqueName(file.getName());

        //文件在存储桶中的key
        String key = prefix + newFileName;

        //声明客户端
        COSClient cosClient = null;
        try {
            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);
            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //执行上传并返回结果信息
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return url + key;
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return null;
    }

    /**
     * upload()重载方法
     *
     * @param multipartFile
     * @return 上传文件在存储桶的链接
     */
    public String upload(MultipartFile multipartFile)  {

        //生成唯一文件名
        String newFileName = generateUniqueName(multipartFile.getOriginalFilename());

        //文件在存储桶中的key
        String key = prefix + newFileName;

        //声明客户端
        COSClient cosClient = null;

        //准备将MultipartFile类型转为File类型
        File file = null;
        try {
            //生成临时文件
            file = File.createTempFile("temp", null);

            //将MultipartFile类型转为File类型
            multipartFile.transferTo(file);

            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);

            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));

            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);


            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);

            //执行上传并返回结果信息
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return url + key;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
            file.delete();
        }
        return null;
    }

    /**
     * 根据UUID生成唯一文件名
     */
    public String generateUniqueName(String originalName) {
        return UUID.randomUUID() + originalName.substring(originalName.lastIndexOf("."));
    }
}

